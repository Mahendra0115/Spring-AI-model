package com.example.redischat.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import org.springframework.ai.chat.messages.*;


import com.example.redischat.prompt.JavaSystemPrompt.UniversalPrompt;
import com.example.redischat.redis.RedisChatMemory;

import reactor.core.publisher.Flux;


@Service
public class ChatService {

	private final ChatModel chatModel;
	private final RedisChatMemory memory;


	public ChatService(ChatModel chatModel, RedisChatMemory memory) {
	this.chatModel = chatModel;
	this.memory = memory;
	}


	public Flux<String> streamChat(String sessionId, String userInput) {


	List<Message> messages = new ArrayList<>();
	messages.add(new SystemMessage(UniversalPrompt.system()));


	List<String> history = memory.getHistory(sessionId, 10);
	if (history != null) {
	history.forEach(m -> {
	if (m.startsWith("USER:"))
	messages.add(new UserMessage(m.substring(5)));
	else if (m.startsWith("AI:"))
	messages.add(new AssistantMessage(m.substring(3)));
	});
	}


	messages.add(new UserMessage(userInput));
	memory.add(sessionId, "USER:" + userInput);


	Prompt prompt = new Prompt(messages);

	
	return chatModel.stream(prompt)
	        .flatMap(chatResponse ->
	                Flux.fromIterable(chatResponse.getResults())
	        )
	        .map(gen -> gen.getOutput().getText());
	}

}