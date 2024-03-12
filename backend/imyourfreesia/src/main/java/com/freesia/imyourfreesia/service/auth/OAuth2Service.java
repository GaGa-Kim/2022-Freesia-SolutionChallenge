package com.freesia.imyourfreesia.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoDto;
import com.freesia.imyourfreesia.except.AccessTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2Service {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN_PARAM = "access_token";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OAuth2UserInfoDto getUserInfoByAccessToken(String accessToken, String userInfoUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(ACCESS_TOKEN_PARAM, accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(userInfoUri, request, String.class);
            return objectMapper.readValue(response.getBody(), OAuth2UserInfoDto.class);
        } catch (RestClientException | JsonProcessingException e) {
            log.error(e.getMessage());
            throw new AccessTokenException();
        }
    }
}