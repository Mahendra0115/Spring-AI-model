package com.example.redischat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.redischat.service.ChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {
	private final ChatService chatService;
	
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
		}


		@GetMapping(value = "/stream", produces = "text/event-stream")
		public Flux<String> chatStream(@RequestParam String sessionId,
		@RequestParam String message) {
		return chatService.streamChat(sessionId, message);
		}
}
