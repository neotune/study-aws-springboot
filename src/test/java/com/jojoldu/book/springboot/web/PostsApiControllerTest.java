package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
    @WebMvcTest 어노테이션의 경우 JPA 기능이 동작하지 않기 때문에
    @SpringBootTest 와 TestRestTemplate 를 이용하여 테스트 코드 작성

 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_게시물_등록() throws Exception {
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_게시물_수정() throws Exception {
        // 게시물 저장
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        // 저장된 게시물에서 id 추출
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        // api request 요청 셋 생성
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        /*
            Spring에서는 HttpEntity라는 클래스를 제공한다
            이 클래스는 Http 프로토콜을 이용하는 통신의 header와 body 관련 정보를 저장할 수 있게 한다
            그리고 이를 상속받은 클래스로 RequestEntity와 ResponseEntity가 존재한다
            즉, 통신 메시지 관련 header와 body의 값들을 하나의 객체로 저장하는 것이 HttpEntity클래스 객체이고
            Request 부분일 경우 HttpEntity를 상속받은 RequestEntity가,
            Response 부분일 경우 HttpEntity를 상속받은 ResponseEntity가 하게 된다
         */

        // http 통신 프로토콜 header 와 body 정보를 하나의 httpEntity 객체에 저장
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // api 호출하여 http response 정보를 ResponseEntity에 저장
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

}