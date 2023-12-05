package pjh5365.linuxserviceweb.domain.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인에 성공했으므로 세션등록
        HttpSession session = request.getSession();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        session.setMaxInactiveInterval(5 * 60);  // 아무동작도 하지않으면 세션은 5분 후 만료
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        // 부모 클래스의 동작 호출 (기본적으로는 리다이렉션)
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
