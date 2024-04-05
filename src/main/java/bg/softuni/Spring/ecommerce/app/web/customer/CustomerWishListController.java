package bg.softuni.Spring.ecommerce.app.web.customer;

import bg.softuni.Spring.ecommerce.app.model.dto.WishListDto;
import bg.softuni.Spring.ecommerce.app.service.WishListService;
import bg.softuni.Spring.ecommerce.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerWishListController {

    private final WishListService wishListService;

    public CustomerWishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/wishlist")
    public ResponseEntity<Long> addProductToWishList(@RequestBody WishListDto wishListDto) {

            Long wishListId = wishListService.addProductToWishList(wishListDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(wishListId);
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishListDto>> getAllProductsInWishList(@PathVariable("userId") Long userId){

            List<WishListDto> allProductsInWishList = wishListService.getAllProductsInWishList(userId);
            return ResponseEntity.ok(allProductsInWishList);
    }
}
