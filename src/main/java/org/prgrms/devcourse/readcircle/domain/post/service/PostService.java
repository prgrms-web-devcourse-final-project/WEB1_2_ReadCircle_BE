package org.prgrms.devcourse.readcircle.domain.post.service;

import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;

import java.util.List;

public interface PostService {
    PostDTO register(PostDTO postDTO);              //게시글 작성

    PostDTO read(Long postId);                      //게시글 상세 조회

    List<PostDTO> readAll();                        //게시글 조회 - 전체

    List<PostDTO> readByUserId(String userId);        //게시글 조회 - 유저 아이디로
    List<PostDTO> readByKeyword(String keyword);        //게시글 조회 - 키워드(제목)로
    List<PostDTO> readByCategory(BookCategory bookCategory);    //게시글 조회 - 카테고리로

    PostDTO update(Long postId, PostDTO postDTO);                //게시글 내용 수정 (제목, 내용, 가격)
    PostDTO updateStatus(Long postId, boolean tradeStatus);      //게시글 판매 유무 수정
    void delete(Long postId);                       //게시글 삭제

}
