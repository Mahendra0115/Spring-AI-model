package com.db.aichat.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.db.aichat.constant.MessageRole;
import com.db.aichat.entitys.Conversation;
import com.db.aichat.entitys.Message;
import com.db.aichat.repository.ConversationRepository;
import com.db.aichat.repository.MessageRepository;
import com.db.aichat.service.ChatService;

import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final MessageRepository messageRepo;
    private final ConversationRepository conversationRepo;

    public ChatServiceImpl(ChatClient.Builder builder,
                           MessageRepository messageRepo,
                           ConversationRepository conversationRepo) {
        this.chatClient = builder.build();
        this.messageRepo = messageRepo;
        this.conversationRepo = conversationRepo;
    }

  

    @Override
    public Flux<String> chatStream(Long userId, Long conversationId, String userMessage) {

        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseGet(() -> {
                    Conversation c = new Conversation();
                    c.setTitle("New Chat");
                    c.setCreatedAt(LocalDateTime.now());
                    return conversationRepo.save(c);
                });

        // Save USER message
        Message userMsg = new Message();
        userMsg.setRole(MessageRole.USER);
        userMsg.setContent(userMessage);
        userMsg.setTimestamp(LocalDateTime.now());
        userMsg.setConversation(conversation);
        messageRepo.save(userMsg);

        // Build history prompt
        List<Message> history =
                messageRepo.findByConversationId(conversation.getId());

        String systemPrompt = """
                You are a knowledgeable AI assistant.
                Answer clearly and confidently.
                """;

        String conversationPrompt = history.stream()
                .map(m -> m.getRole() + ": " + m.getContent())
                .collect(Collectors.joining("\n"));

        String finalPrompt = systemPrompt + "\n" + conversationPrompt;

        StringBuilder fullAiResponse = new StringBuilder();

        //  STREAM RESPONCE FROM AI
        return chatClient.prompt(finalPrompt)
                .stream()
                .content()
//                .map(token -> token.replace("\n", "<br/>"))
                .doOnNext(chunk -> fullAiResponse.append(chunk))
                .doOnComplete(() -> {
                    // Save AI message after stream ends
                    Message aiMsg = new Message();
                    aiMsg.setRole(MessageRole.AI);
                    aiMsg.setContent(fullAiResponse.toString());
                    aiMsg.setTimestamp(LocalDateTime.now());
                    aiMsg.setConversation(conversation);
                    messageRepo.save(aiMsg);
                });
           
        }
    }

