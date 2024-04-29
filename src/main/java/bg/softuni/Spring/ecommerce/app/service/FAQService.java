package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;
import bg.softuni.Spring.ecommerce.app.model.entity.FAQEntity;

import java.util.List;

public interface FAQService {

    FAQEntity createFAQ(FAQDto faqDto);

    List<FAQDto> getAllFaq(Long productId);
}
