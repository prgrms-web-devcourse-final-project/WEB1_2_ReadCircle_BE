package org.prgrms.devcourse.readcircle.domain.wish.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devcourse.readcircle.common.util.PagingUtil;
import org.prgrms.devcourse.readcircle.domain.book.entity.Book;
import org.prgrms.devcourse.readcircle.domain.book.service.BookService;
import org.prgrms.devcourse.readcircle.domain.post.dto.PostDTO;
import org.prgrms.devcourse.readcircle.domain.post.entity.Post;
import org.prgrms.devcourse.readcircle.domain.post.service.PostService;
import org.prgrms.devcourse.readcircle.domain.seller.exception.SellerException;
import org.prgrms.devcourse.readcircle.domain.user.entity.User;
import org.prgrms.devcourse.readcircle.domain.user.service.UserService;
import org.prgrms.devcourse.readcircle.domain.wish.dto.WishDTO;
import org.prgrms.devcourse.readcircle.domain.wish.entity.Wish;
import org.prgrms.devcourse.readcircle.domain.wish.exception.WishException;
import org.prgrms.devcourse.readcircle.domain.wish.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishRepository wishRepository;
    private final UserService userService;
    private final PostService postService;
    private final BookService bookService;
    private final PagingUtil pagingUtil;

    @Override
    public void addPostWish(Long postId, String userId){
        User user = userService.findUserByUserId(userId);
        PostDTO postDTO = postService.read(postId);
        Post post = postDTO.toEntity();

        Wish findWish = wishRepository.findWishPost(post, user).orElse(null);

        if(findWish != null){
            throw WishException.DUPLICATED_WISH_EXCEPTION.getTaskException();
        }else{
            try{
                Wish wish = Wish.builder()
                        .user(user)
                        .post(post)
                        .build();
                wishRepository.save(wish);
            }catch (Exception e){
                throw WishException.NOT_REGISTERED_WISH_EXCEPTION.getTaskException();
            }
        }
    }

    @Override
    public void addBookWish(Long bookId, String userId){
        User user = userService.findUserByUserId(userId);
        Book book = bookService.getBookById(bookId);

        Wish findWish = wishRepository.findWishBook(book, user).orElse(null);

        if(findWish != null){
            throw WishException.DUPLICATED_WISH_EXCEPTION.getTaskException();
        }else {
            try {
                Wish wish = Wish.builder()
                        .user(user)
                        .book(book)
                        .build();
                wishRepository.save(wish);
            } catch (Exception e) {
                throw WishException.NOT_REGISTERED_WISH_EXCEPTION.getTaskException();
            }
        }
    }

    @Override
    public Page<WishDTO> wishList(String userId, int page, int size){
        Pageable pageable = pagingUtil.getNewPageable(page, size);
        Page<Wish> wishList = wishRepository.getWishListByUserId(userId, pageable);
        return wishList.map(WishDTO::new);
    }

    public List<WishDTO> wishs(String userId){
        List<Wish> wishs = wishRepository.getWishs(userId);
        return wishs.stream().map(WishDTO::new).toList();
    }
    @Override
    public void deleteWish(Long wishId){
        try{
            wishRepository.deleteById(wishId);
        } catch(Exception e){
            throw WishException.NOT_REMOVED_WISH_EXCEPTION.getTaskException();
        }
    }
}
