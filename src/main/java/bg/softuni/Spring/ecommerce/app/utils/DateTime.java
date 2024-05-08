package bg.softuni.Spring.ecommerce.app.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface DateTime {
    Date getDate();
}
