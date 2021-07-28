package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Entity 클래스에서는 Setter 메소드를 만들지 않음
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 나타내는 어노테이션
public class Posts {
    @Id // 해당 테이블의 PK 필드를 나태나는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK생성규칙, IDENTITY 옵션을 추가해야 auto increment가 됨
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
