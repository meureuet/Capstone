package com.example.webpro.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue
    @Column(name="chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="room_id")
    private ChatRoom room;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDate;

    @Builder
    public ChatMessage(ChatRoom room, String sender, String message){
        this.room=room;
        this.sender=sender;
        this.message=message;
        this.sendDate=LocalDateTime.now();
    }

    public static ChatMessage createChat(ChatRoom room, String sender, String message){
       return ChatMessage.builder()
               .room(room)
               .sender(sender)
               .message(message)
               .build();
    }
}
