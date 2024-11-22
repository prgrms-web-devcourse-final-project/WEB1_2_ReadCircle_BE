package org.prgrms.devcourse.readcircle.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.domain.comment.dto.CommentDTO;
import org.prgrms.devcourse.readcircle.domain.comment.service.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentServiceImpl commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> register(
            @RequestBody CommentDTO commentDTO,
            @PathVariable("postId") Long postId
    ){
        commentDTO.setPostId(postId);
        return ResponseEntity.ok(commentService.register(commentDTO));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> readAll(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(commentService.readAllByPostId(postId));
    }

    @GetMapping("/my-comments/{nickname}")
    public ResponseEntity<List<CommentDTO>> readAllByUserId(@PathVariable("nickname") String nickname){
        return ResponseEntity.ok(commentService.readAllByNickname(nickname));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable("commentId") Long commentId){
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}
