package com.hansungmarket.demo.dto.board;

import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;

    private String writer;

    private String title;

    private String goodsName;

    private String goodsCategory;

    private String content;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    private List<Long> boardImageIds;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.writer = entity.getUser().getNickname();
        this.title = entity.getTitle();
        this.goodsName = entity.getGoodsName();
        this.goodsCategory = entity.getGoodsCategory();
        this.content = entity.getContent();
        this.createdDateTime = entity.getCreatedDateTime();
        this.modifiedDateTime = entity.getModifiedDateTime();
        this.boardImageIds = getImageIds(entity.getBoardImages());
    }

    // 게시글에 존재하는 이미지 id 가져오기
    private List<Long> getImageIds(List<BoardImage> boardImages) {
        List<Long> imageIds = new ArrayList<>();
        for(BoardImage boardImage : boardImages) {
            imageIds.add(boardImage.getId());
        }

        return imageIds;
    }
}
