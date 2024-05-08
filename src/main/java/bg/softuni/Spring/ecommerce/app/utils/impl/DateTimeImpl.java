package bg.softuni.Spring.ecommerce.app.utils.impl;

import bg.softuni.Spring.ecommerce.app.utils.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateTimeImpl implements DateTime {
    @Override
    public Date getDate() {
        return new Date();
    }
}
