package com.kyoni.plog.service.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.domain.UserRoleEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	// UserDbService는 인터페이스다. 해당 인터페이스를 구현하고 있는 객체가 Bean으로 등록되어 있어야 한다.
	@Autowired
	private MemberServiceImpl memberService;

	@Autowired
	private HttpSession httpSession;
	
	// 사용자가 로그인할 때 아이디를 입력하면 loadUserByUsername에 인자로 전달한다.
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// loginId에 해당하는 정보를 데이터베이스에서 읽어 CustomUser객체에 저장한다.
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", userId);
		map.put("oauthKey", userId);
		UserEntity customUser = memberService.getUser(map);

		// 해당 아이디에 해당하는 정보가 없으면 UsernameNotFoundException이 발생
		if (customUser == null)
			throw new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다.");

		// 정보가 있을 경우엔 UserDetails인터페이스를 구현한 객체를 리턴
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setEmail(customUser.getEmail());
		customUserDetails.setPassword(customUser.getPwd());
		customUserDetails.setUsername(customUser.getEmail());

		List<UserRoleEntity> customRoles = memberService.getUserRoles(customUser.getSeq());
		// 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
		// 리스트에 추가한다. MemberRole 이름은 "ROLE_"로 시작되야 한다.
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (customRoles != null) {
			for (UserRoleEntity customRole : customRoles) {
				authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));
			}
		}

		// SessioUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
		httpSession.setAttribute("user", new SessionUser(customUser));
		
		// CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
		customUserDetails.setAuthorities(authorities);
		customUserDetails.setEnabled(true);
		customUserDetails.setAccountNonExpired(true);
		customUserDetails.setAccountNonLocked(true);
		customUserDetails.setCredentialsNonExpired(true);
		return customUserDetails;
	}
	
}