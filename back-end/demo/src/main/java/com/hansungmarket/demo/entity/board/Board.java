package com.hansungmarket.demo.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansungmarket.demo.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_category")
    private String goodsCategory;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<BoardImage> boardImages = new ArrayList<>();

    @Builder
    private Board(String title, String goodsName, String goodsCategory, String content, User user) {
        this.title = title;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.content = content;
        this.user = user;
    }

    // 작성자 정보 저장
    public void setUser(User user) {
        this.user = user;
    }

    // 게시글 업데이트 기능
    public void update(String title, String goodsName, String goodsCategory, String content, List<BoardImage> boardImages) {
        this.title = title;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.content = content;
        this.boardImages = boardImages;
    }
}
