package bg.softuni.Spring.ecommerce.app.service;

import bg.softuni.Spring.ecommerce.app.model.dto.AddProductInCardDto;
import bg.softuni.Spring.ecommerce.app.model.dto.CartItemDto;

public interface CartItemService {


    CartItemDto addProductToCart (AddProductInCardDto addProductInCardDto);

    boolean isCartItemPresent(AddProductInCardDto addProductInCardDto);



}
