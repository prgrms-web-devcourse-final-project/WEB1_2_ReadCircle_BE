package org.prgrms.devcourse.readcircle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:/application-dev.properties")
class ReadCircleApplicationTests {

    @Test
    void contextLoads() {
    }

}
