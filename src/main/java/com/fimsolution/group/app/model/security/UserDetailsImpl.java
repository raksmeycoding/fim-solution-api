package com.fimsolution.group.app.model.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Builder
public class UserDetailsImpl implements UserDetails {


    private UUID id;
    private final String username;
    private final String password;

    private boolean enabled;
    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;

//    public UserDetailsImpl(UUID id, String username, String password,
//                           Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }


    public static UserDetailsImpl buildUserCredentials(UserCredentials user) {
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getName().toUpperCase()))
                .collect(Collectors.toList());

//        return new UserDetailsImpl(
//                user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                authorities);

        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .authorities(authorities)
                .password(user.getPassword())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
