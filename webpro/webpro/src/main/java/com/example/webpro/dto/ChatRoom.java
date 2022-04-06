package com.example.webpro.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name="room_id")
    private Long id;
    private String name;

    @Builder
    public ChatRoom(String name){
        this.name=name;
    }

    public static ChatRoom createRoom(String name){
        return ChatRoom.builder().name(name).build();
    }
}
