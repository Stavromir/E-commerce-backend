package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.entity.CategoryEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HexFormat;

@Component
public class ProductTestDataUtil {

    public static final String IMG_HEX = "e05f";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryTestDataUtil categoryTestDataUtil;


    public ProductEntity createProduct() {
        byte[] img = HexFormat.of().parseHex(IMG_HEX);
        CategoryEntity category = categoryTestDataUtil.createCategory();

        ProductEntity product = new ProductEntity()
                .setName("product")
                .setDescription("productDescription")
                .setPrice(1000L)
                .setImg(img)
                .setCategory(category);

        return productRepository.save(product);
    }
}
