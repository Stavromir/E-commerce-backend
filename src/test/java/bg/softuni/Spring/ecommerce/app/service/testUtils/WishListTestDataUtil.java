package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.WishListEntity;
import bg.softuni.Spring.ecommerce.app.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListTestDataUtil {


    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private UserTestDataUtil userTestDataUtil;

    public WishListEntity createWishListEntity() {
        UserEntity testUserEntity = userTestDataUtil.getTestUserInstance();
        ProductEntity testProductEntity = productTestDataUtil.createProduct();

        WishListEntity wishListEntity = new WishListEntity()
                .setProduct(testProductEntity)
                .setUser(testUserEntity);

        return wishListRepository.save(wishListEntity);
    }

    public WishListDto createWishListDto() {
        UserEntity testUserEntity = userTestDataUtil.getTestUserInstance();
        ProductEntity testDataUtilProduct = productTestDataUtil.createProduct();

        return new WishListDto()
                .setUserId(testUserEntity.getId())
                .setProductId(testDataUtilProduct.getId());
    }

    public void clearAllTestData() {
        wishListRepository.deleteAll();
        productTestDataUtil.clearAllTestData();
    }
}
