package com.db.aichat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.aichat.entitys.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUserId(Long userId);
}
