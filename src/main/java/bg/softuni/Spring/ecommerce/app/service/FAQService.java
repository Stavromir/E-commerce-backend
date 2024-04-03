package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.FAQDto;

import java.util.List;

public interface FAQService {

    Long createFAQ(FAQDto faqDto);

    List<FAQDto> getAllFaq(Long productId);
}
