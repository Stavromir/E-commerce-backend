package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.repository.WishListRepository;
import bg.softuni.Spring.ecommerce.app.service.WishListService;
import bg.softuni.Spring.ecommerce.app.service.testUtils.ProductTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.UserTestDataUtil;
import bg.softuni.Spring.ecommerce.app.service.testUtils.WishListTestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishListServiceImplTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private WishListTestDataUtil wishListTestDataUtil;


    @Test
    void testAddProductToWishList() {
        WishListDto testWishListDto = wishListTestDataUtil.createWishListDto();

    }

}