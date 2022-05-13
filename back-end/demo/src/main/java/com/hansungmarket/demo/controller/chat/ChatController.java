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
    @GetMapping("/myChatRoom")
    @ApiOperation(value = "채팅방 목록 찾기", notes = "내 채팅목록 출력")
    public List<ChatRoomDto> searchMyChatRoom(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return chatService.searchMyChatRoom(principalDetails.getUserId());
    }

    // 나와 상대방 채팅방 id  출력
    @GetMapping("/chatRoom/{receiverId}")
    @ApiOperation(value = "상대 id로 채팅방 찾기", notes = "상대방 id로 채팅방 찾기")
    public Long searchChatRoom(@PathVariable Long receiverId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return chatService.searchChatRoomByUser(principalDetails.getUserId(), receiverId);
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
        chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);
        
        // 메시지 전송
        simpMessagingTemplate.convertAndSend("/topic/" + chatMessageRequestDto.getChatRoomId(),
                chatMessageRequestDto.getMessage());
    }

//    // 메세지 저장 테스트
//    @PostMapping("/message")
//    public Long saveMessage(@RequestBody ChatMessageRequestDto chatMessageRequestDto, Authentication authentication){
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return chatService.saveChatMessage(principalDetails.getUserId(), chatMessageRequestDto);
//    }
}
