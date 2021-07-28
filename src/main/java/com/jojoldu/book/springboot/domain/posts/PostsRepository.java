package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
    Repository 어노테이션 추가 필요 없음
    주의사항 : Entity 클래스와 EntityRepository 인터페이스는 같은 패키지 내에 위치 해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
