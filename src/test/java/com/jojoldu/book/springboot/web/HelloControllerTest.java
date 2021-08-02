package com.jojoldu.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class)
/*
    @WebMvcTest는 JPA 설정을 지원하는 기능이 없음
    Application.java에 @EnableJpaAuditing 기능을 활성화 할 경우 JPA 기능을 지원하지 않는 @WebMvcTest는 아래와 같이 에러를 낸다.
    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext':
    Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: JPA metamodel must not be empty!
    해결 할 수 있는 방법은 2가지
    1. JpaAuditing용 configuration.java 파일 생성 후 @Configuration, @EnableJpaAuditing 어노테이션 추가
    2. 테스트 파일에 @MockBean 추가
 */
@MockBean(JpaMetamodelMappingContext.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}