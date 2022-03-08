package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardRequestDto;
import com.hansungmarket.demo.dto.board.BoardResponseDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import com.hansungmarket.demo.repository.board.BoardRepository;
import com.hansungmarket.demo.repository.board.BoardRepositoryCustom;
import com.hansungmarket.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageService boardImageService;
    private final UserRepository userRepository;
    private final BoardRepositoryCustom boardRepositoryCustom;

    // 모든 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchAll() {
        return boardRepository.findAllCustom().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // id로 게시글 검색
    @Transactional(readOnly = true)
    public BoardResponseDto searchById(Long id) {
        Board board = boardRepository.findByIdCustom(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new BoardResponseDto(board);
    }

    // 카테고리 검색 /////// 나중에 동적 쿼리로
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByCategory(String category) {
        return boardRepository.findByGoodsCategory(category).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // username 으로 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUsername(String username) {
        return boardRepository.findByUsernameCustom(username).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // nickname 으로 게시글 검색
    @Transactional(readOnly = true)
    public List<BoardResponseDto> searchByUserId(Long id) {
        return boardRepository.findByUserId(id).stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }
    
    // 게시글 생성
    @Transactional
    public BoardResponseDto create(BoardRequestDto requestDto, List<MultipartFile> images, String username) throws IOException {
        Board tempBoard = requestDto.toEntity();
        // 유저정보 저장
        tempBoard.setUser(userRepository.findByUsername(username));

        // 게시글 저장
        Board createdBoard = boardRepository.save(tempBoard);

        // 이미지가 없는 경우
        if (CollectionUtils.isEmpty(images)) {
            return new BoardResponseDto(createdBoard);
        }

        // 이미지가 있는 경우
        for (MultipartFile image : images) {
            // 이미지 저장
            BoardImage boardImage = boardImageService.create(createdBoard, image);
            createdBoard.getBoardImages().add(boardImage);
        }

        return new BoardResponseDto(createdBoard);
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, List<MultipartFile> images, String username) throws IOException {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        List<BoardImage> boardImages = boardImageService.searchByBoardId(id);

        String boardUsername = board.getUser().getUsername();
        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(boardUsername, username)) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        // 수정 전 게시글에 이미지 존재하면 전부 삭제
        if (!CollectionUtils.isEmpty(boardImages)) {
            // 이미지 파일 삭제
            for (BoardImage image : boardImages) {
                boardImageService.deleteFile(image);
            }

            // entity 이미지 목록 지우면 db에 반영(db 정보 삭제)
            board.getBoardImages().clear();
        }

        // 수정 후 게시글에 이미지 존재하면 삽입
        if (!CollectionUtils.isEmpty(images)) {
            for (MultipartFile image : images) {
                boardImageService.create(board, image);
            }
        }

        // 게시글 수정사항 업데이트
        board.update(requestDto.getTitle(),
                requestDto.getGoodsName(),
                requestDto.getGoodsCategory(),
                requestDto.getContent(),
                board.getBoardImages());

        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id, String username) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        List<BoardImage> boardImages = boardImageService.searchByBoardId(id);

        String boardUsername = board.getUser().getUsername();
        // 현재 사용자와 게시글 작성자가 다른 경우
        if (!Objects.equals(boardUsername, username)) {
            throw new RuntimeException("작성자가 일치하지 않습니다.");
        }

        // 게시글에 이미지가 없는 경우
        if (CollectionUtils.isEmpty(boardImages)) {
            // 게시글 삭제
            boardRepository.deleteById(id);
            return;
        }

        //게시글에 이미지가 있는 경우
        for (BoardImage boardImage : boardImages) {
            // 이미지 파일 삭제
            boardImageService.deleteFile(boardImage);
        }

        // 게시글 삭제
        boardRepository.deleteById(id);
    }
}
