package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;

import java.util.List;

public interface WishListService {

    Long addProductToWishList(WishListDto wishListDto);

    List<WishListDto> getAllProductsInWishList(Long userId);
}
