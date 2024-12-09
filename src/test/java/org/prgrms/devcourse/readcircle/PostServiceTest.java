package org.prgrms.devcourse.readcircle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.devcourse.readcircle.domain.post.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    private PostServiceImpl postService;

    @BeforeAll
    static void beforeAll(){

    }

    @Test
    @DisplayName("직거래 게시글 추가")
    public void addPost(){

    }
}
