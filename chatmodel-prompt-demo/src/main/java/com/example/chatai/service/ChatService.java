package com.example.chatai.service;

import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.*;
import com.example.chatai.prompt.JavaPromptConfig;

@Service
public class ChatService {
    
	
	private ChatModel  chatModel;
	
	public ChatService (ChatModel chatModel) {
		this.chatModel = chatModel;
	}
	
	
	 public String ask(String userInput) {
		 
		 List <Message> messages = List.of(
				 JavaPromptConfig.systemMessage(),
				 new UserMessage("What is JVM?"),
				 new AssistantMessage("JVM is a runtime environment that executes Java bytecode."),
				 new UserMessage("What is Spring Boot?"),
				 new AssistantMessage("Spring Boot simplifies Spring development using auto-configuration."),
				 new UserMessage(userInput)
				 
				 );
		 Prompt prompt = new Prompt(messages) ; 
				 return chatModel.call(prompt)
				          .getResult()
				          .getOutput()
				          .getText();
		 
	 }
	
}

