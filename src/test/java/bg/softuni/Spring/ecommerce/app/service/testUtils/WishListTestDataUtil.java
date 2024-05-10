package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListTestDataUtil {
    @Autowired
    private ProductTestDataUtil productTestDataUtil;
    @Autowired
    private UserTestDataUtil userTestDataUtil;

    public WishListDto createWishListDto() {
        UserEntity testUserEntity = userTestDataUtil.createTestUser();
        ProductEntity testDataUtilProduct = productTestDataUtil.createProduct();

        return new WishListDto()
                .setUserId(testUserEntity.getId())
                .setProductId(testDataUtilProduct.getId());
    }
}
