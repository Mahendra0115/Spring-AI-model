package com.db.aichat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.aichat.entitys.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationId(Long conversationId);
}