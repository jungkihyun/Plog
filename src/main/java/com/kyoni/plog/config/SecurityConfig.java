package com.kyoni.plog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kyoni.plog.service.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// CustomUserDetailsService 객체 주입
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**");
	}

	/*
	 * WebSecurityConfigurerAdapter가 가지고 있는 void
	 * configure(AuthenticationManagerBuilder auth)를 오버라이딩 AuthenticationFilter가
	 * 아이디/암호를 입력해서 로그인 할 때 처리해주는 필터이고 아이디에 해당하는 정보를 데이터베이스에서 읽어 들일 때
	 * UserDetailsService를 구현하고 있는 객체를 이용한다(Spring Security 개요 참조)
	 * UserDetailsService는 인터페이스이고, 해당 인터페이스를 구현하고 있는 Bean을 사용 주입된
	 * CustomUserDetailsService객체를
	 * auth.userDetailsService(customUserDetailsService)로 설정하고 있다. 이렇게 설정된 객체는
	 * 아이디/암호를 입력 받아 로그인을 처리하는 AuthenticationFilter에서 사용 CustomUserDetailsService는
	 * UserDetailsService를 구현하고 있는 객체여야 한다.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}

	/*
	 * 로그인 과정없이 사용할 수 있는 경로 추가("/", "/main", "/members/loginerror",
	 * "/members/joinform", "/members/join", "/members/welcome") "/securepage",
	 * "/members/**"는 로그인도 되어 있어야 하고 "USER"권한도 가지고 있어야 접근할 수 있도록 설정 로그인 폼은
	 * "/members/loginform"이 경로라는 것을 의미. 해당 경로가 요청 왔을 때 로그인 폼을 보여주는 컨트롤러 메소드를 작성해야
	 * 한다. 로그인 폼에서 input태그의 이름은 "userId", "password"이어야 한다는 설정을 하고 있다. ex. <input
	 * type="text" name="userId">, <input type="password" name="password"> 아이디와 암호를
	 * 입력 받아 로그인 처리를 해주는 경로는 "/authenticate"로 설정 "/authenticate" 경로는 직접 구현하는 것이 아니라,
	 * 아래와 같이 설정만 해주면 Spring Security Filter가 해당 경로를 검사하다가 아이디가 전달되면 로그인을 처리해준다.
	 * <form method="post" action="/securityexam/authenticate"> 와 같이 action 설정해야한다.
	 * 프로젝트의 Context Path가 "/securityexam"이기 때문에 "/securityexam/authenticate"이다. 만약
	 * 로그인 처리가 실패하게 되면 "/loginerror?login_error=1"로 forwarding 된다. 해당 경로를 처리하는 컨트롤러
	 * 메소드는 개발자가 작성해야한다. 로그인을 성공하게 되면 "/"로 redirect 한다. permitAll()이 붙어 있다는 것은 해당
	 * 로그인 폼이 아무나 접근 가능하다는 것을 의미한다.(로그인 페이지를 로그인 후에 접근할 수는 없으므로) "/logout"요청이 오면
	 * 세션에서 로그인 정보를 삭제한 후 "/"로 redirect
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/user/login")
				.usernameParameter("email")
				.passwordParameter("pwd")
				.loginProcessingUrl("/user/login")
				.failureForwardUrl("/user/loginerror?login_error=1")
				.defaultSuccessUrl("/", true)
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}