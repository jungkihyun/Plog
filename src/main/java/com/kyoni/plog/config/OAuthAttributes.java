package com.kyoni.plog.config;

import java.util.Map;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String,Object> attributes;
	private String nameAttributeKey, username, email, oauthKey, picture;
	
	@Builder
	public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String username, String email, String oauthKey, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.username = username;
		this.email = email;
		this.oauthKey = oauthKey;
		this.picture = picture;
	}
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//		if("naver".equals(registrationId)){
//			return ofNaver("id", attributes);
//		}

		if("kakao".equals(registrationId)){
			return ofKakao("id", attributes);
		}

		return ofGoogle(userNameAttributeName, attributes);
	}
	
	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.username((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.oauthKey((String) attributes.get("sub"))
				.picture((String) attributes.get("picture"))
				.build();
	}
	public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
		Map<String,Object> profile = (Map<String, Object>) response.get("profile");
		return OAuthAttributes.builder()
				.username((String) profile.get("nickname"))
				.email((String) response.get("email"))
				.picture((String) profile.get("profile_image_url"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.oauthKey(String.valueOf(attributes.get("id")))
				.build();
	}
	public UserEntity toEntity() {
		return UserEntity.builder()
				.username(username)
				.email(email)
				.role(Role.ANONYMOUS)
				.oauthKey(oauthKey)
				.picture(picture)
				.build();
	}

}