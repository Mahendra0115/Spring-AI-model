package com.example.browserchat.prompt;

import org.springframework.ai.chat.messages.SystemMessage;

public class JavaSystemPrompt {
    
		
		public static SystemMessage system(){
			return new SystemMessage("""
			You are a Java expert chatbot.
			Answer ONLY Java, Spring, Spring Boot related questions.
			If question is outside Java, reply:
			"I can answer only Java-related questions."
			Remember previous conversation context.
			""");
	}
}
