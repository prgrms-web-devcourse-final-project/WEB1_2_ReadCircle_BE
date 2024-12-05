package org.prgrms.devcourse.readcircle.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.payment.dto.response.PaymentInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class PortOneClient {

    private final RestTemplate restTemplate;

    @Value("${imp.key}")
    private String impKey;

    @Value("${imp.secret}")
    private String impSecret;


    private String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문 설정: x-www-form-urlencoded 형식으로 변환
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("imp_key", impKey);
        body.add("imp_secret", impSecret);

        // HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || responseBody.get("response") == null) {
            throw new RuntimeException("Failed to retrieve access token");
        }

        Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
        log.info("access_token : "+responseMap.get("access_token"));
        return (String) responseMap.get("access_token");
    }

    public PaymentInfo getPaymentInfo(String impUid) {
        String url = "https://api.iamport.kr/payments/" + impUid;
        log.info("url : " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        log.info("Response Status Code: {}", response.getStatusCode());

        Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");

        if (response.getBody() == null) {
            log.error("Response body is null");
        } else {
            log.info("Response body: {}", response.getBody());
            log.info("a:{}", responseData.get("status"));
            log.info("b:{}", responseData.get("amount"));
        }


        return new PaymentInfo((String)responseData.get("status"), (Integer) responseData.get("amount"));
    }
}
