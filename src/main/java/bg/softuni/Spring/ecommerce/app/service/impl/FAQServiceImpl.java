package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;
    private final ProductService productService;

    public FAQServiceImpl(FAQRepository faqRepository, ProductService productService) {
        this.faqRepository = faqRepository;
        this.productService = productService;
    }

    @Override
    public Long createFAQ(FAQDto faqDto) {

        ProductEntity product = productService.getProductById(faqDto.getProductId());

        FAQEntity faq = new FAQEntity()
                .setQuestion(faqDto.getQuestion())
                .setAnswer(faqDto.getAnswer())
                .setProduct(product);

        return faqRepository.save(faq).getId();
    }
}
