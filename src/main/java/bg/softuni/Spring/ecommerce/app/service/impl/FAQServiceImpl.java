package bg.softuni.Spring.ecommerce.app.service.impl;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;
import bg.softuni.Spring.ecommerce.app.model.entity.ProductEntity;
import bg.softuni.Spring.ecommerce.app.repository.FAQRepository;
import bg.softuni.Spring.ecommerce.app.service.FAQService;
import bg.softuni.Spring.ecommerce.app.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;
    private final ProductService productService;

    public FAQServiceImpl(FAQRepository faqRepository,
                          @Lazy ProductService productService) {
        this.faqRepository = faqRepository;
        this.productService = productService;
    }

    @Override
    public FAQEntity createFAQ(FAQDto faqDto) {

        ProductEntity product = productService.getProductById(faqDto.getProductId());

        FAQEntity faq = new FAQEntity()
                .setQuestion(faqDto.getQuestion())
                .setAnswer(faqDto.getAnswer())
                .setProduct(product);

        return faqRepository.save(faq);
    }

    @Override
    public List<FAQDto> getAllFaq(Long productId) {
        return faqRepository.getAllByProductId(productId)
                .stream()
                .map(FAQServiceImpl::mapToFaqDto)
                .collect(Collectors.toList());
    }

    private static FAQDto mapToFaqDto(FAQEntity faqEntity) {
        return new FAQDto()
                .setAnswer(faqEntity.getAnswer())
                .setQuestion(faqEntity.getQuestion())
                .setId(faqEntity.getId());
    }
}
