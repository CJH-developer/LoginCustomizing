package com.project.myTest.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	// 비밀번호 암호화 Bean 등록
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		
		http.authorizeRequests( (a) -> a
						.antMatchers("/main/**").permitAll()
						.antMatchers("/join/**").permitAll()
						.antMatchers("/user/**").hasRole("USER")
						.antMatchers("/com/**").hasRole("COM")
						.antMatchers("/test").permitAll()
						.anyRequest().authenticated()
				);
		
		http.formLogin()
			.loginPage("/main")
			.loginProcessingUrl("/login/loginForm")
			.defaultSuccessUrl("/test")
			.failureHandler(authenticationFailureHandler())
			.and()
			.logout().logoutUrl("/myLogout").logoutSuccessUrl("/main")
			;

		http.rememberMe()
			.key("jaehongCookies")
			.tokenValiditySeconds(3600) // 1시간
			.rememberMeParameter("remember-me") // 화면에서 전달되는 checkbox의 파라미터 값
			.userDetailsService(myUserDetailsService) // remeber me 토큰이 존재할 때 실행시킬 서비스 
			.authenticationSuccessHandler(authenticationSuccessHandler());
		
		
		return http.build();
	}
	
	// 인증 실패 핸들러
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		CustomAuthenticationFailure custom = new CustomAuthenticationFailure();
		custom.setRedirectURL("/login?err=true");
		
		return custom;
	}
	
	// 자동로그인 핸들러
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomRememberMeHandler();
	}
	
}
