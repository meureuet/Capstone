package com.hansungmarket.demo.controller.etc;

import com.hansungmarket.demo.dto.user.UserDto;
import com.hansungmarket.demo.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"기타 기능"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EtcController {
    private final UserService userService;

    // 판매왕 랭킹
    @GetMapping("/saleRank")
    @ApiOperation(value = "판매왕 랭킹", notes = "판매왕 1~5등 출력")
    public List<UserDto> getSaleRank() {
        return userService.getSaleRank();
    }
    
    // 많이 거래된 상품
    
    // 많이 찜한 상품
}
