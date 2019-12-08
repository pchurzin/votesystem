package ru.pchurzin.votesystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.pchurzin.votesystem.model.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class VoteSystemUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private static final GrantedAuthority ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");
    private static final GrantedAuthority USER_GRANTED_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");
    private final User user;

    public VoteSystemUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ("admin".equalsIgnoreCase(getUsername())) {
            return Arrays.asList(USER_GRANTED_AUTHORITY, ADMIN_GRANTED_AUTHORITY);
        }
        return Collections.singleton(USER_GRANTED_AUTHORITY);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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

    public User getUser() {
        return user;
    }
}
