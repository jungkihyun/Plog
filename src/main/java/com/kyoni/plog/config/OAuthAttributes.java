package com.kyoni.plog.config;

import java.util.Map;

import com.kyoni.plog.domain.UserEntity;
import com.kyoni.plog.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String,Object> attributes;
	private String nameAttributeKey, nicname, email;
	
	@Builder
	public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String nicname, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.nicname = nicname;
		this.email = email;
	}
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}
	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.nicname((String) attributes.get("nicname"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	public UserEntity toEntity() {
		return UserEntity.builder()
				.nicname(nicname)
				.email(email)
				.role(Role.ANONYMOUS)
				.build();
	}

}