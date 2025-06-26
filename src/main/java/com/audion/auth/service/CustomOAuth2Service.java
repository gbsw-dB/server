package com.audion.auth.service;

import com.audion.auth.domain.CustomUserDetails;
import com.audion.auth.domain.UserAccount;
import com.audion.auth.domain.UserRole;
import com.audion.auth.domain.dto.KakaoResponse;
import com.audion.auth.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        com.audion.auth.domain.dto.OAuth2Response response;
        if (request.getClientRegistration().getRegistrationId().equals("kakao")) {
            response = new KakaoResponse(user.getAttributes());
        } else {
            throw new IllegalArgumentException("사용할 수 없는 인증방법입니다.");
        }

        String provider = response.getProvider();
        String providerId = response.getProviderId();
        String username = provider + "_" + providerId;
        String email = response.getEmail();

        Optional<UserAccount> byUsername = userAccountRepository.findByUsername(username);
        UserAccount account;
        if (byUsername.isEmpty()) {
            account = account.builder()
                    .username(username)
                    .email(email)
                    .password(bCryptPasswordEncoder.encode(username + "kakao"))
                    .role(UserRole.ROLE_USER)
                    .build();

            userAccountRepository.save(account);
        } else {
            account = byUsername.get();
        }

        return new CustomUserDetails(account);
    }

}