package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // JPA Auditing 활성화
/*
    @WebMvcTest는 JPA 설정을 지원하는 기능이 없음
    Application.java에 @EnableJpaAuditing 기능을 활성화 할 경우 JPA 기능을 지원하지 않는 @WebMvcTest는 아래와 같이 에러를 낸다.
    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext':
    Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: JPA metamodel must not be empty!
    해결 할 수 있는 방법은 2가지
    1. JpaAuditing용 configuration.java 파일 생성 후 @Configuration, @EnableJpaAuditing 어노테이션 추가
    2. 테스트 파일에 @MockBean 추가 ex: @MockBean(JpaMetamodelMappingContext.class)
 */
public class JpaConfig {
}
