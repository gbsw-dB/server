package com.audion.auth.domain.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        Object idObj = attribute.get("id");
        return idObj != null ? idObj.toString() : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null) {
            Object emailObj = kakaoAccount.get("email");
            if (emailObj != null) {
                return emailObj.toString();
            }
        }
        return null;
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        if (properties != null) {
            Object nicknameObj = properties.get("nickname");
            if (nicknameObj != null) {
                return nicknameObj.toString();
            }
        }
        return null;
    }
}