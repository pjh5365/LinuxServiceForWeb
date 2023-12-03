package pjh5365.linuxserviceweb.domain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pjh5365.linuxserviceweb.domain.user.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getRole();    // enum 을 가져와서 enum 의 role String 으로 가져오기
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {   // 사용자의 비밀번호 리턴
        return user.getPassword();
    }

    @Override
    public String getUsername() {   // 사용자의 name 이 아닌 username(ID) 리턴
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
