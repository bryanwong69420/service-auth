package com.mcp.authservice.service;

import com.mcp.authservice.dto.vo.RefreshTokenVo;
import com.mcp.authservice.dto.vo.RoleVo;
import com.mcp.authservice.dto.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<RoleVo> roles;
    private String last_login_at;
    private RefreshTokenVo refreshTokenVo;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl buildVo(UserVo vo) {
        List<GrantedAuthority> authorities = vo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(vo.getId(), vo.getUsername(),  vo.getEmail(), vo.getPassword(), vo.getRoles(),
                vo.getLastLoginAt(), vo.getRefreshTokenVo(), authorities);
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
