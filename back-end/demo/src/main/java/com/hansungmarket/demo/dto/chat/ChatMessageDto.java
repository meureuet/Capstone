package com.hansungmarket.demo.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private String message;

    private Long userId;
    private String nickname;

    private LocalDateTime createdDateTime;

    @Builder
    private ChatMessageDto(String message, Long userId, String nickname, LocalDateTime createdDateTime){
        this.message = message;
        this.userId = userId;
        this.nickname = nickname;
        this.createdDateTime = createdDateTime;
    }
}
