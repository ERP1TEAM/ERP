package com.quickkoala.token.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    
    // SecurityConfig 생성자: JwtTokenProvider와 CustomUserDetailsService를 주입받습니다.
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accessDeniedHandler = accessDeniedHandler;
    }
    
    // SecurityFilterChain 빈: Spring Security의 보안 설정을 구성합니다
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> 
            	authorizeRequests
                // 정적 리소스에 대한 접근을 허용
                .requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**", "/img/**").permitAll()
                .requestMatchers("/api/**", "/register", "/login").permitAll()
                .requestMatchers("/sales/**").hasAuthority("Sales")
                .requestMatchers("/main/**").hasAuthority("Main")
                .requestMatchers("/supplier/**").hasAuthority("Supplier")
                .requestMatchers(HttpMethod.DELETE,"/**").permitAll()
                .anyRequest().authenticated()
        )
            .formLogin(formLogin ->
	            formLogin
	                .loginPage("/login")
	                .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
	                .permitAll()
            )
            .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(accessDeniedHandler) // 권한 거부 시 핸들러 설정
            )
            .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
    // JwtAuthenticationFilter 빈: JWT 인증 필터를 생성합니다.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(this.jwtTokenProvider);
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }
    
    // AuthenticationManager 빈: 인증을 관리하는 AuthenticationManager를 생성합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
