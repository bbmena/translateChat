package com.chat.translate.repository;

import com.chat.translate.model.ChatLogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogEntryRepository extends JpaRepository<ChatLogEntry, Long> {

    Page<ChatLogEntry> findAll(Pageable pageable);
}
