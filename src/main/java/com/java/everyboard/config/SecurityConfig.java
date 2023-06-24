package com.java.everyboard.config;

import com.java.everyboard.security.auth.filter.JwtAuthenticationFilter;
import com.java.everyboard.security.auth.filter.JwtVerificationFilter;
import com.java.everyboard.security.auth.handler.*;
import com.java.everyboard.security.auth.jwt.JwtTokenizer;
import com.java.everyboard.security.utils.CustomAuthorityUtils;
import com.java.everyboard.security.utils.RedisUtils;
import com.java.everyboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisUtils redisUtils;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                    .loginPage("/oauth2login")// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                    .usernameParameter("email")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
                    .passwordParameter("password")		// 로그인 시 form에서 가져올 값
                    .loginProcessingUrl("/social")		// 로그인을 처리할 URL 입력
                    .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
                    .failureUrl("/oauth2login")		//로그인 실패 시 /loginForm으로 이동
                .and()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.GET, "/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/user/join","/auth/email").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/auth/password/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/user").hasRole("USER")
                        .antMatchers("/h2/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers("/oauth2/**", "/loading/**","/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, userRepository,redisUtils)));

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @CrossOrigin
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisUtils);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, redisUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
