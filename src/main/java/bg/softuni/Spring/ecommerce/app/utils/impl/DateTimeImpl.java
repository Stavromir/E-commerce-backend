package bg.softuni.Spring.ecommerce.app.utils.impl;

import bg.softuni.Spring.ecommerce.app.utils.DateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DateTimeImpl implements DateTime {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
