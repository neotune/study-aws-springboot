package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // h2-console 화면을 사용하기 위해 disable
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 disable
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // 전체열람가능
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한을 가진 사람만 열람 가능
                    .anyRequest().authenticated() // 그외 URL은 로그인한 사용자에게 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃후 / 으로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
