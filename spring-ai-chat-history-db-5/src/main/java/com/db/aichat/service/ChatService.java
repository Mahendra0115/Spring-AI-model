package com.db.aichat.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    Flux<String> chatStream(Long userId, Long conversationId, String userMessage);
}
