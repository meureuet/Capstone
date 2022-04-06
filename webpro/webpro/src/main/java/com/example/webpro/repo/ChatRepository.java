package com.example.webpro.repo;

import com.example.webpro.dto.ChatMessage;
import com.example.webpro.dto.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;


    //채팅방 찾기
    public ChatRoom findRoomById(String id){
        return em.find(ChatRoom.class, id);
    }

    public List<ChatRoom> findAllRooms(){
        return em.createQuery("select r from ChatRoom r", ChatRoom.class).getResultList();
    }
    //채팅보내기
    public void chatSave(ChatMessage chatMessage){
        em.persist(chatMessage);
    }
    //채팅방만들기
    public ChatRoom  createChatRoom(String name){
        ChatRoom room=new ChatRoom(name);
        em.persist(room);
        return room;
    }
    //채팅꺼내보기

}
