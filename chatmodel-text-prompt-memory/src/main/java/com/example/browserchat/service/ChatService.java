package com.example.browserchat.service;

import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.example.browserchat.memory.InMemoryChatStore;
import com.example.browserchat.prompt.JavaSystemPrompt;

import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    //  STREAMING METHOD
    public Flux<String> chatStream(String sessionId, String userInput) {

        List<Message> history = InMemoryChatStore.getHistory(sessionId);

        if (history.isEmpty()) {
            history.add(JavaSystemPrompt.system());
        }

        history.add(new UserMessage(userInput));

        Prompt prompt = new Prompt(history);

        StringBuilder fullReply = new StringBuilder();

        return chatModel.stream(prompt)
                .map(response -> {
                    String token = response.getResult()
                                           .getOutput()
                                           .getText();

                    fullReply.append(token);
                    return token;
                })
                .doOnComplete(() -> {
                    // 🔹 Save final AI message in memory
                    history.add(new AssistantMessage(fullReply.toString()));
                });
    }
}
