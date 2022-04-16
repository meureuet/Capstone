package com.hansungmarket.demo.controller.board;

import com.hansungmarket.demo.service.board.BoardImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = {"이미지"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BoardImageController {
    private final BoardImageService boardImageService;

    // id로 이미지 출력
    @GetMapping(value = "/images/{id}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "이미지 출력", notes = "해당 id를 가진 이미지 출력")
    public ResponseEntity<byte[]> showBoardImage(@PathVariable Long id) throws IOException {
        String imagePath = boardImageService.getImagePath(id);
        InputStream imageStream = new FileInputStream(imagePath);

        // byte 로 이미지 인코딩
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

}
