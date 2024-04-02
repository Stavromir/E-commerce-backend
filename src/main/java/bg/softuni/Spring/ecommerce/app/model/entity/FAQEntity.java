package bg.softuni.Spring.ecommerce.app.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "faqs")
public class FAQEntity extends BaseEntity{

    private String question;
    private String answer;
    private ProductEntity product;

    public FAQEntity() {
    }

    public String getQuestion() {
        return question;
    }

    public FAQEntity setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public FAQEntity setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    @ManyToOne
    public ProductEntity getProduct() {
        return product;
    }

    public FAQEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }
}
