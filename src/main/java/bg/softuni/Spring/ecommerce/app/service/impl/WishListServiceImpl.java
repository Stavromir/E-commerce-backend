package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.WishListEntity;
import bg.softuni.Spring.ecommerce.app.repository.WishListRepository;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.service.WishListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final ProductService productService;

    public WishListServiceImpl(WishListRepository wishListRepository,
                               UserService userService,
                               ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public Long addProductToWishList(WishListDto wishListDto) {

        if (wishListRepository.existsByProductId(wishListDto.getProductId())) {
            throw new IllegalArgumentException("Product already in wishlist");
        }

        ProductEntity product = productService.getProductById(wishListDto.getProductId());
        UserEntity user = userService.getUserById(wishListDto.getUserId());

        WishListEntity wishListEntity = new WishListEntity();
        wishListEntity
                .setProduct(product)
                .setUser(user);

        return wishListRepository.save(wishListEntity).getId();
    }

    @Override
    public List<WishListDto> getAllProductsInUserWishList(Long userId) {
        return wishListRepository.getAllByUserId(userId)
                .stream()
                .map(WishListServiceImpl::mapToWishListDto)
                .collect(Collectors.toList());
    }

    private static WishListDto mapToWishListDto (WishListEntity wishListEntity) {
        return new WishListDto()
                .setId(wishListEntity.getId())
                .setPrice(wishListEntity.getProduct().getPrice())
                .setProductDescription(wishListEntity.getProduct().getDescription())
                .setProductName(wishListEntity.getProduct().getName())
                .setReturnedImg(wishListEntity.getProduct().getImg());
    }
}
