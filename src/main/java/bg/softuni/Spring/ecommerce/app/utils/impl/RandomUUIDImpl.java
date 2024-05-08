package bg.softuni.Spring.ecommerce.app.utils.impl;

import bg.softuni.Spring.ecommerce.app.utils.RandomUUID;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomUUIDImpl implements RandomUUID {
    @Override
    public UUID createRandomUUID() {
        return UUID.randomUUID();
    }
}
