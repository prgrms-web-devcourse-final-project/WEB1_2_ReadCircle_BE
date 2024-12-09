package org.prgrms.devcourse.readcircle;

import org.junit.jupiter.api.Test;
import org.prgrms.devcourse.readcircle.domain.payment.dto.response.PaymentInfo;
import org.prgrms.devcourse.readcircle.domain.payment.service.PortOneClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:/application-dev.properties")
class ReadCircleApplicationTests {

    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    public PortOneClient portOneClient;

    // Logger 객체 생성
//    private static final Logger log = LoggerFactory.getLogger(ReadCircleApplicationTests.class);
//
//    @Test
//    public void getPaymentInfo() {
//        String impUid = "imp_589204346374";
//        String url = "https://api.iamport.kr/payments/" + impUid;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(portOneClient.getAccessToken());
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
//        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
//
//        log.info("Response Status Code: {}", response.getStatusCode());
//
//        Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
//
//        if (response.getBody() == null) {
//            log.error("Response body is null");
//        } else {
//            log.info("Response body: {}", response.getBody());
//            log.info("a:{}", responseData.get("status"));
//            log.info("b:{}", responseData.get("amount"));
//        }
//
//    }

}
