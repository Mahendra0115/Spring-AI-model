package com.db.aichat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.aichat.entitys.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

