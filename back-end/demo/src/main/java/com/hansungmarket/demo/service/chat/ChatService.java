package com.hansungmarket.demo.service.chat;

import com.hansungmarket.demo.dto.chat.ChatRoomDto;
import com.hansungmarket.demo.entity.chat.ChatRoom;
import com.hansungmarket.demo.entity.user.User;
import com.hansungmarket.demo.repository.chat.ChatMessageRepository;
import com.hansungmarket.demo.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 생성
    public Long create(Long userId1, Long userId2) {
        User user1 = User.builder().id(userId1).build();
        User user2 = User.builder().id(userId2).build();

        ChatRoom chatRoom = ChatRoom.builder()
                .user1(user1)
                .user2(user2)
                .build();

        return chatRoomRepository.save(chatRoom).getId();
    }

    // 상대방으로 채팅방 검색
    public Long searchChatRoomByUser(Long myId, Long receiverId) {
        return null;
    }

    // 내 채팅방 찾기
    public List<ChatRoomDto> searchMyChatRoom(Long userId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findIdByUserId(userId);

        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        for(ChatRoom chatRoom : chatRoomList) {
            // 채팅방의 user1이 자신이면
            ChatRoomDto chatRoomDto;
            if(Objects.equals(chatRoom.getUser1().getId(), userId)) {
                // user2 정보 저장
                chatRoomDto = ChatRoomDto.builder()
                        .id(chatRoom.getId())
                        .partnerId(chatRoom.getUser2().getId())
                        .partnerNickname(chatRoom.getUser2().getNickname())
                        .build();

            } else {
                // user1이 자신이 아니면
                // user1 정보 저장
                chatRoomDto = ChatRoomDto.builder()
                        .id(chatRoom.getId())
                        .partnerId(chatRoom.getUser1().getId())
                        .partnerNickname(chatRoom.getUser1().getNickname())
                        .build();

            }
            chatRoomDtoList.add(chatRoomDto);
        }
        // 상대방 정보 담아서 리턴
        return chatRoomDtoList;
    }



}
