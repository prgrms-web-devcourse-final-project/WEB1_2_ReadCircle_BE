package org.prgrms.devcourse.readcircle.domain.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.prgrms.devcourse.readcircle.common.upload.PostImageRepository;
import org.prgrms.devcourse.readcircle.common.util.PagingUtil;
import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.common.enums.BookCategory;
import org.prgrms.devcourse.readcircle.domain.post.exception.PostException;
import org.prgrms.devcourse.readcircle.domain.post.repository.PostRepository;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.repository.UserRepository;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserService userService;
    private final PagingUtil pagingUtil;

    //게시글 등록
    @Override
    public PostDTO register(PostDTO postDTO, MultipartFile bookImage, MultipartFile bookAPIImage, String userId) {
        try{
            //책 이미지 설정
            if (bookImage != null && !bookImage.isEmpty()) {
                String bookImageName = postImageRepository.upload(bookImage);
//                String bookAPIImageName = postImageRepository.upload(bookAPIImage);
                postDTO.setBookImage(bookImageName);
//                postDTO.setBookAPIImage(bookAPIImageName);
            }

            //사용자 검사 및 설정
            User user = userService.findUserByUserId(userId);
            Post savedPost = postDTO.toEntity();
            savedPost.setUser(user);

            postRepository.save(savedPost);
            return new PostDTO(savedPost);
        } catch (Exception e){
            throw PostException.NOT_FOUND_EXCEPTION.getTaskException();
        }
    }

    //게시글 상세 조회
    @Override
    public PostDTO read(Long postId){
        Post foundPost = postRepository.findById(postId).orElseThrow(PostException.NOT_FOUND_EXCEPTION::getTaskException);
        return new PostDTO(foundPost);
    }

    //게시글 전체 조회
    @Override
    public Page<PostDTO> readAll(String sortType, String order){
        Pageable pageable = pagingUtil.getPageable(sortType, order);
        Page<PostDTO> postDTOList = postRepository.getAll(pageable);
        return postDTOList;
    }

    //게시글 사용자 아이디로 조회
    @Override
    public Page<PostDTO> readByUserId(String userId, String sortType, String order){
        Pageable pageable = pagingUtil.getPageable(sortType, order);
        Page<PostDTO> postList = postRepository.getPostByUserId(userId, pageable);
        return postList;
    }

    //키워드(제목)로 게시글 조회
    @Override
    public Page<PostDTO> readByKeyword(String keyword, String sortType, String order){
        Pageable pageable = pagingUtil.getPageable(sortType, order);
        Page<PostDTO> postDTOList = postRepository.getPostByKeyword(keyword, pageable);
        return postDTOList;
    }

    //카테고리로 게시글 조회
    @Override
    public Page<PostDTO> readByCategory(BookCategory bookCategory, String sortType, String order){
        Pageable pageable = pagingUtil.getPageable(sortType, order);
        Page<PostDTO> postDTOList = postRepository.getPostByCategory(bookCategory, pageable);
        return postDTOList;
    }

    //게시글 내용 수정
    @Override
    public PostDTO update(Long postId, PostDTO postDTO){
        Post post = postRepository.findById(postId).orElseThrow(PostException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            post.changePostTitle(postDTO.getTitle());
            post.changePostContent(postDTO.getContent());
            post.changePostPrice(postDTO.getPrice());

            return new PostDTO(postRepository.save(post));
        }catch (Exception e){
            throw PostException.NOT_MODIFIED_EXCEPTION.getTaskException();
        }
    }

    //게시글 상태 유무 수정
    @Override
    public PostDTO updateStatus(Long postId, boolean tradeStatus){
        Post post = postRepository.findById(postId).orElseThrow(PostException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            post.changeTradeStatus(tradeStatus);
            return new PostDTO(postRepository.save(post));
        }catch (Exception e){
            throw PostException.NOT_MODIFIED_EXCEPTION.getTaskException();
        }
    }

    //게시글 삭제하기
    @Override
    public void delete(Long postId){
        postRepository.findById(postId).orElseThrow(PostException.NOT_FOUND_EXCEPTION::getTaskException);
        try{
            postRepository.deleteById(postId);
        }catch (Exception e){
            throw PostException.NOT_REMOVED_EXCEPTION.getTaskException();
        }
    }
}
