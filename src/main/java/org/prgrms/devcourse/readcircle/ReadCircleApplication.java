package org.prgrms.devcourse.readcircle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReadCircleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadCircleApplication.class, args);
    }

}
