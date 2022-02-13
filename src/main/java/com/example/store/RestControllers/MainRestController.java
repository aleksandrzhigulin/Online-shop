package com.example.store.RestControllers;


import com.example.store.Models.Cart;
import com.example.store.Models.Product;
import com.example.store.Models.User;
import com.example.store.Repositories.CartRepository;
import com.example.store.Repositories.ProductRepository;
import com.example.store.Repositories.UserRepository;
import com.example.store.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class MainRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("cart/add/{id}")
    public String addItemToCart(@PathVariable(name = "id") Long productId) {
        User user = userService.getAuthorizedUser();
        Product product = productRepository.findById(productId).orElseThrow();
        Cart cart = cartRepository.findById(user.getId()).orElseThrow();
        Map<Product, Integer> userCart = cart.getCart();


        // Check if this item is already exists in the user's cart
        if (userCart.containsKey(product)) {
            int amount = userCart.get(product);
            amount++;
            userCart.put(product, amount);
        }
        else {
            userCart.put(product, 1);
        }
        cart.setCart(userCart);
        cartRepository.save(cart);
        return "ok";
    }
}
