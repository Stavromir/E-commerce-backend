package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.Size;

public class FAQDto {

    private Long id;
    private String question;
    private String answer;
    private Long productId;

    public Long getId() {
        return id;
    }

    public FAQDto setId(Long id) {
        this.id = id;
        return this;
    }

    @Size(min = 10)
    public String getQuestion() {
        return question;
    }

    public FAQDto setQuestion(String question) {
        this.question = question;
        return this;
    }

    @Size(min = 10)
    public String getAnswer() {
        return answer;
    }

    public FAQDto setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public FAQDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }
}
