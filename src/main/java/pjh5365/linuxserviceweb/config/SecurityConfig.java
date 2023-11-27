package pjh5365.linuxserviceweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/register", "/registerProc", "/css/**", "/js/**").permitAll()   // 메인화면, 로그인, 회원가입, css, js 에 대한 요청은 누구나
                        .requestMatchers("/log/**").hasRole("ADMIN")    // 로그에 관련된 요청들은 ADMIN 권한만 허용
                        .anyRequest().authenticated()   // 나머지 요청은 로그인한 사용자들만
                );


        http.csrf((csrf) -> csrf.disable());    // csrf 는 우선 비활성화

        http.formLogin((auth) -> auth
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
        );

        return http.build();
    }
}
