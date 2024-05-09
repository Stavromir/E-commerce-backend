package bg.softuni.Spring.ecommerce.app.service.testUtils;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FAQTestDataUtil {

    public static final String FAQ_QUESTION = "testFaqQuestion";
    public static final String FAQ_ANSWER = "testFaqAnswer";

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductTestDataUtil productTestDataUtil;

    public FAQEntity createFaqEntity() {

        ProductEntity testProduct = productTestDataUtil.createProduct();

        FAQEntity testFAQ = new FAQEntity()
                .setQuestion(FAQ_QUESTION)
                .setAnswer(FAQ_ANSWER)
                .setProduct(testProduct);

        return faqRepository.save(testFAQ);
    }

    public FAQDto createFaqDto() {

        ProductEntity testProduct = productTestDataUtil.createProduct();

        return new FAQDto()
                .setQuestion(FAQ_QUESTION)
                .setAnswer(FAQ_ANSWER)
                .setProductId(testProduct.getId());
    }

    public void clearAllTestData() {
        faqRepository.deleteAll();
    }
}
