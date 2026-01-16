package com.example.chatbot.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;


@Service
public class ChatService {
	
	private OllamaChatModel chatModel;
	
	
	public ChatService(OllamaChatModel chatModel) {
		 this.chatModel = chatModel;
		 
	}
	
	public String ask(String message) {
		
		return chatModel.call(message);
	}
    
	
	
//  Streming Response  token by token 

	public Flux<String> askStreaming(String message) {

        Prompt prompt = new Prompt(message); // Pre-defined Spring AI class

        return chatModel.stream(prompt)      // Flux<ChatResponse>
                .map(chatResponse ->
                        chatResponse.getResult()
                                .getOutput()  
                                .getText()

                );
    }
    
	

	
	
	
	
	
	
	
	
	
	
	
}
