package org.prgrms.devcourse.readcircle.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.entity.enums.BookCategory;
import org.prgrms.devcourse.readcircle.domain.post.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    @Value("${post.image.upload.path}")
    private String uploadPath;

    //게시글 등록
    @PostMapping("/create")
    public ResponseEntity<PostDTO> create(
            @RequestPart(name = "postDTO") PostDTO postDTO,
            @RequestPart(name = "bookImage", required = false) MultipartFile bookImage
        ) throws IOException {


        if(bookImage != null && !bookImage.isEmpty()){
            String fileName = System.currentTimeMillis() + "_" + bookImage.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path filePath = Paths.get(uploadPath, fileName);
            bookImage.transferTo(filePath.toFile());
            postDTO.setBookImage(filePath.toString());
        }
        return ResponseEntity.ok(postServiceImpl.register(postDTO));
    }

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> read(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postServiceImpl.read(postId));
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<Page<PostDTO>> readAll(
            @RequestParam(defaultValue = "postCreatedAt") String sortType,
            @RequestParam(defaultValue = "desc") String order
    ){
        Page<PostDTO> posts = postServiceImpl.readAll(sortType, order);
        return ResponseEntity.ok(posts);
    }

    //게시글 사용자 아이디로 조회
    @GetMapping("/search/{userId}")
    public ResponseEntity<Page<PostDTO>> readByUserId(
            @PathVariable("userId") String userId,
            @RequestParam(defaultValue = "postCreatedAt") String sortType,
            @RequestParam(defaultValue = "desc") String order
    ){
        Page<PostDTO> posts = postServiceImpl.readByUserId(userId, sortType, order);
        return ResponseEntity.ok(posts);
    }

    //키워드(제목)로 게시글 조회
    @GetMapping("/search/keyword/{title}")
    public ResponseEntity<Page<PostDTO>> readByKeyword(
            @PathVariable("title") String title,
            @RequestParam(defaultValue = "postCreatedAt") String sortType,
            @RequestParam(defaultValue = "desc") String order
    ){
        Page<PostDTO> posts = postServiceImpl.readByKeyword(title, sortType, order);
        return ResponseEntity.ok(posts);
    }

    //카테고리로 게시글 조회
    @GetMapping("/search/category/{category}")
    public ResponseEntity<Page<PostDTO>> readByCategory(
            @PathVariable("category") BookCategory bookCategory,
            @RequestParam(defaultValue = "postCreatedAt") String sortType,
            @RequestParam(defaultValue = "desc") String order
        ){
        Page<PostDTO> posts = postServiceImpl.readByCategory(bookCategory, sortType, order);
        return ResponseEntity.ok(posts);
    }

    //게시글 내용 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> upadte(@PathVariable("postId") Long postId,
                                            @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postServiceImpl.update(postId, postDTO));
    }

    //게시글 판매 유무 수정
    @PutMapping("/{postId}/status")
    public ResponseEntity<PostDTO> updateStatus(@PathVariable("postId") Long postId,
                                                @RequestBody  PostDTO postDTO){
        return ResponseEntity.ok(postServiceImpl.updateStatus(postId, postDTO.isTradeStatus()));
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId){
        postServiceImpl.delete(postId);
        return ResponseEntity.ok().build();
    }
}
