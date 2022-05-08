package com.hansungmarket.demo.controller.chat;

import com.hansungmarket.demo.config.auth.PrincipalDetails;
import com.hansungmarket.demo.dto.chat.ChatRoomDto;
import com.hansungmarket.demo.service.chat.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

}
