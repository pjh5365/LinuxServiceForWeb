package pjh5365.linuxserviceweb.domain.auth.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import pjh5365.linuxserviceweb.domain.auth.service.SecondaryAuthService;

public class SecondaryAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final SecondaryAuthService secondaryAuthService;


    @Autowired
    public SecondaryAuthFilter(SecondaryAuthService secondaryAuthService) {
        super("/loginProc");
        this.secondaryAuthService = secondaryAuthService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String emailAuth = request.getParameter("email-auth");

        if(!secondaryAuthService.checkSecondaryCode(username, emailAuth))    // 2차 인증 코드와 맞지않다면 로그인에 실패하기 위해 비밀번호를 틀리게 설정
            password = password + "!!!!";

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username.trim(), password);
        setDetails(request, authenticationToken);
        return getAuthenticationManager().authenticate(authenticationToken);
    }
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
