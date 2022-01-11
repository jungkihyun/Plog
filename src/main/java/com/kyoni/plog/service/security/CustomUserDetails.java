package com.kyoni.plog.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
	private String id;
	private String username;
	private String password;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private Collection<? extends GrantedAuthority> authorities;

}