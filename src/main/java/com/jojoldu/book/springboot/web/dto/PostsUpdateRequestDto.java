package com.jojoldu.book.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    DTO = Data Transfer Object
    Entity 클래스를 Request의 Response 클래스(Model) 으로 사용하면 안된다
    View Layer 와 DB Layer의 역할 분리를 철저하게 하는게 좋다
 */
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
    }
}
