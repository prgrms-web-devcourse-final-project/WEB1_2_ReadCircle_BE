package org.prgrms.devcourse.readcircle.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PagingUtil {

    private int defaultPage = 0;
    private int defaultSize = 10;

    public Pageable getPageable(String sortType, String order){
        Sort.Direction dir = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(defaultPage, defaultSize, Sort.by(dir, sortType));
    }
}
