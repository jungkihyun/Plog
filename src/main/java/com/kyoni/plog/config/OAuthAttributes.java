package com.kyoni.plog.config;

import java.util.Map;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String,Object> attributes;
	private String nameAttributeKey, username, email, googleSub;
	
	@Builder
	public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String username, String email, String googleSub) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.username = username;
		this.email = email;
		this.googleSub = googleSub;
	}
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}
	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.username((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.googleSub((String) attributes.get("sub"))
				.build();
	}
	public UserEntity toEntity() {
		return UserEntity.builder()
				.username(username)
				.email(email)
				.role(Role.ANONYMOUS)
				.googleSub(googleSub)
				.build();
	}

}