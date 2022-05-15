package com.hansungmarket.demo.controller.chat;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.chat.ChatMessageRequestDto;
import com.hansungmarket.demo.dto.chat.ChatMessageResponseDto;
import com.hansungmarket.demo.dto.chat.ChatRoomDto;
import com.hansungmarket.demo.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"채팅"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    // 내 채팅방 목록 출력
    @GetMapping("/chatRoom")
    @ApiOperation(value = "채팅방 목록 찾기", notes = "내 채팅방 목록 출력, receiverId로 상대방과의 채팅방 출력")
    public List<ChatRoomDto> searchChatRoom(@RequestParam(required = false) Long receiverId,
                                              Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if(receiverId == null){
            return chatService.searchMyChatRoom(principalDetails.getUserId());
        }

        ChatRoomDto chatRoomDto = chatService.searchChatRoomByUser(principalDetails.getUserId(), receiverId);
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        chatRoomDtoList.add(chatRoomDto);

        return chatRoomDtoList;
    }

    // 채팅목록 출력
    @GetMapping("/chatRoom/{chatRoomId}/chatMessage")
    @ApiOperation(value = "채팅방 id로 채팅내역 출력", notes = "채팅방 id로 채팅내역 출력")
    public List<ChatMessageResponseDto> searchChatMessage(@PathVariable Long chatRoomId) {
        return chatService.searchChatMessage(chatRoomId);
    }

    @MessageMapping("/chat")
    public void sendChatMessage(ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 채팅정보 저장
        ChatMessageResponseDto chatMessageResponseDto = chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);

        // 메시지 전송
        simpMessagingTemplate.convertAndSend("/topic/" + chatMessageRequestDto.getChatRoomId(),
                chatMessageResponseDto);
    }

//    // 메세지 저장 테스트
//    @PostMapping("/message")
//    public Long saveMessage(@RequestBody ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);
//    }
}
