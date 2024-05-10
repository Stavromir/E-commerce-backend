package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.model.entity.WishListEntity;
import bg.softuni.Spring.ecommerce.app.repository.WishListRepository;
import bg.softuni.Spring.ecommerce.app.service.WishListService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.WishListTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishListServiceImplTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private WishListTestDataUtil wishListTestDataUtil;

    @BeforeEach
    void setUp() {
        wishListTestDataUtil.clearAllTestData();
    }

    @AfterEach
    void tearDown() {
        wishListTestDataUtil.clearAllTestData();
    }


    @Test
    void testAddProductToWishList() {
        WishListDto testWishListDto = wishListTestDataUtil.createWishListDto();

        Long wishListEntityId = wishListService.addProductToWishList(testWishListDto);

        Optional<WishListEntity> testWishListEntity = wishListRepository
                .findById(wishListEntityId);

        assertTrue(testWishListEntity.isPresent());
        WishListEntity wishListEntity = testWishListEntity.get();
        assertEquals(testWishListDto.getProductId(), wishListEntity.getProduct().getId());
        assertEquals(testWishListDto.getUserId(), wishListEntity.getUser().getId());
    }

    @Test
    void testAddProductToWishListThrowExc() {
        wishListTestDataUtil.createWishListEntity();
        WishListDto testWishListDto = wishListTestDataUtil.createWishListDto();

        assertThrows(
                IllegalArgumentException.class,
                () -> wishListService.addProductToWishList(testWishListDto)
        );
    }

    @Test
    void testGetAllProductsInWishList() {
        WishListEntity wishListEntity = wishListTestDataUtil.createWishListEntity();

        List<WishListDto> allProductsInUserWishList = wishListService
                .getAllProductsInUserWishList(wishListEntity.getUser().getId());

        WishListDto wishListDto = allProductsInUserWishList.get(0);

        assertNotNull(wishListDto);
        assertEquals(wishListEntity.getId(), wishListDto.getId());
        assertEquals(wishListEntity.getProduct().getPrice(), wishListDto.getPrice());
        assertEquals(wishListEntity.getProduct().getDescription(), wishListDto.getProductDescription());
        assertEquals(wishListEntity.getProduct().getName(), wishListDto.getProductName());
        assertArrayEquals(wishListEntity.getProduct().getImg(), wishListDto.getReturnedImg());
    }

}