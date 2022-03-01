package com.kyoni.plog.service.security;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.kyoni.plog.config.OAuthAttributes;
import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.enums.Role;
import com.kyoni.plog.mapper.UserMapper;
import com.kyoni.plog.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	private MemberServiceImpl memberService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		// 현재 로그인 진행 중인 서비스를 구분하는 코드
		String registrationId = userRequest
				.getClientRegistration()
				.getRegistrationId();
		// oauth2 로그인 진행 시 키가 되는 필드값
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUserNameAttributeName();
		// OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
		OAuthAttributes attributes = OAuthAttributes
				.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		UserEntity user = saveOrUpdate(attributes);
		// SessioUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
		httpSession.setAttribute("user", new SessionUser(user));
		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
				attributes.getAttributes(),
				attributes.getNameAttributeKey()
		);
	}
	private UserEntity saveOrUpdate(OAuthAttributes attributes) {
		String email = attributes.getEmail();
		UserEntity user = memberService.getUser(email);
		if(user == null){
			user = attributes.toEntity();
			UserVO vo = new UserVO(user.getEmail(), user.getNicname(), user.getPwd() != null ? user.getPwd() : "");
			memberService.addUser(vo);
		} else {
			if("ROLE_USER".equals(user.getAuthorityCode())) {
				user.setRole(Role.USER);
			} else if("ROLE_ADMIN".equals(user.getAuthorityCode())) {
				user.setRole(Role.ADMIN);
			} else {
				user.setRole(Role.ANONYMOUS);
			}
		}

		return user;
	}
}