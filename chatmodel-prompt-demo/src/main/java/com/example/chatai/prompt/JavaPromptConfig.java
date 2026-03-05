package com.example.chatai.prompt;

import org.springframework.ai.chat.messages.SystemMessage;

public class JavaPromptConfig {
	
	public static SystemMessage systemMessage(){
		
		return new SystemMessage("""
				You are a senior Java backend assistant.
                Answer ONLY Java, Spring, Spring Boot, Hibernate, Microservices related questions.
                If the question is not Java-related, reply exactly:
                "I can answer only Java-related questions."
                 Keep answers short and interview-ready.
				""");
	}

}
