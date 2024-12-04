package org.prgrms.devcourse.readcircle.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Component
public class PagingUtil {

    private int defaultPage = 0;
    private int defaultSize = 10;
    private String defaultSortType = "createdAt";
    private Sort.Direction defaultDir = ASC;


    public Pageable getPageable(String sortType, String order){
        Sort.Direction dir = order.equalsIgnoreCase("asc") ? ASC : Sort.Direction.DESC;
        return PageRequest.of(defaultPage, defaultSize, Sort.by(dir, sortType));
    }

    public Pageable getNewPageable(int page, int size){
        return PageRequest.of(page,size);
    }
}
