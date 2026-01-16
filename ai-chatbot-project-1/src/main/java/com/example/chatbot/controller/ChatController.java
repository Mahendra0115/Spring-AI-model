package com.example.chatbot.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.chatbot.service.ChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

	private ChatService chatService;
	
	public ChatController (ChatService chatService) {
		this.chatService = chatService;
	}
	
	
	@PostMapping
	public String chat(@RequestBody String message) {
		return chatService.ask(message);
	}
	
	
//	@PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<String> chatStream(@RequestBody String message) {
//	    return chatService.askStreaming(message);
//	}
	
	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> chatStream(@RequestParam String message) {
	    return chatService.askStreaming(message);
	}
	
	

	
	
	
}
