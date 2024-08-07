package com.yedam.app.sgi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.yedam.app.sgi.handler.LoginFailHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}

	@Bean
	SecurityFilterChain filterChin(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/", "/main", "/find/**", "/review/**", "/request/**", "/cht/**", "/alm/**", "/adm/**", "/api/**", "/verify/**", "/login/**", "/signUp/**", "/css/**", "/fonts/**", "/images/**", "/js/**" , "/display").permitAll()
				.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/worker/**").hasAnyRole("WORKER", "ADMIN")
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").usernameParameter("id")
				.defaultSuccessUrl("/main")
				.failureHandler(loginFailHandler()) //로그인실패시 처리하는 핸들러
			.and()
			.logout()
				.logoutSuccessUrl("/main")
			.and()
			.exceptionHandling().accessDeniedPage("/error/403")
			.and()
			.csrf().disable();
		return http.build();
	}
}
