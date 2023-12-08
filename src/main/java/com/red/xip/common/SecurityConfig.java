package com.red.xip.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean   // spring-boot-starter-security cors 설정하기
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.httpBasic().disable(); //스프링 시큐리티에서 HTTP 기본 인증(Basic Authentication)을 비활성화
        http.csrf().disable();  /* 외부 POST 요청을 받아야하니 "csrf"는 꺼준다. */
        http.cors(); // ⭐ CORS를 커스텀하려면 이렇게
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeHttpRequests()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated();

        return http.build();
    }
}
