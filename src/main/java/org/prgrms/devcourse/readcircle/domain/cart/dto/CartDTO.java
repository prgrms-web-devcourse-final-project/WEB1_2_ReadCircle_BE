package org.prgrms.devcourse.readcircle.domain.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private String userId;

    private List<CartItemDTO> cartItemDTOList;
}
