package com.example.browserchat.controlller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.example.browserchat.service.ChatService;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/chat")
public class ChatController {

	
	private ChatService chatService;
	
	public ChatController(ChatService chatService) {
		this.chatService= chatService;
	}
	
	
//	@PostMapping
//	public String chat(@RequestParam String sessionId , @RequestBody String message) {
//		
//		return chatService.chat(sessionId , message);
//	}
	
	
	@PostMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
	    public Flux<String> chatStream(@RequestParam String sessionId,
	                                   @RequestBody String message) {

	        return chatService.chatStream(sessionId, message);
	    }
	
}
