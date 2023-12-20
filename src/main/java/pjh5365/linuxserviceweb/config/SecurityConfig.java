package pjh5365.linuxserviceweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import pjh5365.linuxserviceweb.domain.auth.CustomAuthenticationProvider;
import pjh5365.linuxserviceweb.domain.auth.filter.SecondaryAuthFilter;
import pjh5365.linuxserviceweb.domain.auth.handler.CustomAuthenticationFailureHandler;
import pjh5365.linuxserviceweb.domain.auth.handler.CustomAuthenticationSuccessHandler;
import pjh5365.linuxserviceweb.domain.auth.service.SecondaryAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationFailureHandler failureHandler;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final SecondaryAuthService secondaryAuthService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SecurityConfig(CustomAuthenticationFailureHandler failureHandler, CustomAuthenticationSuccessHandler successHandler, SecondaryAuthService secondaryAuthService, CustomAuthenticationProvider customAuthenticationProvider, AuthenticationConfiguration authenticationConfiguration) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.secondaryAuthService = secondaryAuthService;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        ProviderManager providerManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        providerManager.getProviders().add(customAuthenticationProvider);
        return providerManager;
    }

    @Bean
    public SecondaryAuthFilter secondaryAuthFilter() throws Exception { // 로그인을 할 필터 빈으로 등록
        SecondaryAuthFilter secondaryAuthFilter = new SecondaryAuthFilter(secondaryAuthService);
        secondaryAuthFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        secondaryAuthFilter.setAuthenticationFailureHandler(failureHandler);
        secondaryAuthFilter.setAuthenticationSuccessHandler(successHandler);

        return secondaryAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login","/secondaryAuth", "/register", "/registerProc", "/css/**", "/js/**").permitAll()   // 메인화면, 로그인, 회원가입, css, js 에 대한 요청은 누구나
                        .requestMatchers("/log/**").hasRole("ADMIN")    // 로그에 관련된 요청들은 ADMIN 권한만 허용
                        .anyRequest().authenticated()   // 나머지 요청은 로그인한 사용자들만
                );


        http.csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));    // csrf 활성화

        http.formLogin((auth) -> auth
                .loginPage("/login"));

        http.logout((auth) -> auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/"));

        http.addFilterBefore(secondaryAuthFilter(), UsernamePasswordAuthenticationFilter.class);    // 커스텀 필터를 부착해 로그인을 함
        return http.build();
    }
}
