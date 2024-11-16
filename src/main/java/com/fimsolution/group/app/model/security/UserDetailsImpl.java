package com.fimsolution.group.app.model.security;

import com.fimsolution.group.app.repository.RoleRepository;
import com.fimsolution.group.app.repository.UserRoleRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
@Builder
public class UserDetailsImpl implements UserDetails {


    private String id;
    private final String username;
    private final String password;

    private boolean enabled;
    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;

    private final RoleRepository roleRepository;

    private static UserRoleRepository userRoleRepository;


//    public UserDetailsImpl(UUID id, String username, String password,
//                           Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }


    public static UserDetailsImpl buildUserCredentials(UserCredential userCredential) {

        Collection<GrantedAuthority> authorities = userCredential.getUserRole().stream()
                .map(usersRole -> new SimpleGrantedAuthority(usersRole.getRole().getName().toUpperCase()))
                .collect(Collectors.toList());


        return UserDetailsImpl.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .authorities(authorities)
                .password(userCredential.getPassword())
                .accountNonExpired(userCredential.isAccountNonExpired())
                .accountNonLocked(userCredential.isAccountNonLocked())
                .credentialsNonExpired(userCredential.isCredentialsNonExpired())
                .enabled(userCredential.isEnabled())
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
