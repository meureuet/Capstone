package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import com.hansungmarket.demo.repository.board.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardImageService {
    private final BoardImageRepository boardImageRepository;

    // 이미지 저장될 경로
    @Value("${image-path}")
    private String imagePath;

    // id로 파일 경로 가져오기
    @Transactional(readOnly = true)
    public String getImagePath(Long id) {
        BoardImage image = boardImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
        return image.getStoredFilePath() + File.separator + image.getStoredFileName();
    }

    // 이미지 저장(파일, DB)
    @Transactional
    public void create(Board board, MultipartFile image) throws IOException {
        // 원래 이름
        String imageName = image.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        // 저장될 이름
        String storedImageName = uuid + "_" + imageName;

        File saveFile = new File(imagePath, storedImageName);
        // 파일 저장
        image.transferTo(saveFile);

        BoardImage boardImage = BoardImage.builder()
                .originalFileName(imageName)
                .storedFileName(storedImageName)
                .storedFilePath(imagePath)
                .board(board)
                .build();

        boardImageRepository.save(boardImage);
    }
    
    // 실제 이미지 파일 삭제
    @Transactional
    public void deleteFile(BoardImage image) {
        File deleteFile = new File(image.getStoredFilePath(), image.getStoredFileName());
        deleteFile.delete();
    }

}
