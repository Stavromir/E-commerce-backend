package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCartDto;
import bg.softuni.Spring.ecommerce.app.model.dto.ProductDto;
import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HexFormat;

@Component
public class ProductTestDataUtil {

    public static final String IMG_HEX = "e05f";
    public static final String PRODUCT_NAME = "testProduct";
    public static final String PRODUCT_DESCRIPTION = "testDescription";
    public static final Long PRODUCT_PRICE = 1000L;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryTestDataUtil categoryTestDataUtil;


    public ProductEntity createProduct() {
        byte[] img = HexFormat.of().parseHex(IMG_HEX);
        CategoryEntity category = categoryTestDataUtil.createCategory();

        ProductEntity product = new ProductEntity()
                .setName(PRODUCT_NAME)
                .setDescription(PRODUCT_DESCRIPTION)
                .setPrice(PRODUCT_PRICE)
                .setImg(img)
                .setCategory(category);

        return productRepository.save(product);
    }

    public ProductDto createProductDto() {
        CategoryEntity category = categoryTestDataUtil.createCategory();

        return new ProductDto()
                .setName(PRODUCT_NAME)
                .setDescription(PRODUCT_DESCRIPTION)
                .setPrice(PRODUCT_PRICE)
                .setCategoryId(category.getId());
    }

    public void clearAllTestData() {
        productRepository.deleteAll();
        categoryTestDataUtil.cleanAllTestData();
    }
}
