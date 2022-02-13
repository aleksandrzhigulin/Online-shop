package com.example.store.Controllers;


import com.example.store.Models.Cart;
import com.example.store.Models.Product;
import com.example.store.Models.User;
import com.example.store.Repositories.CartRepository;
import com.example.store.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public String getItemsInCart(Model model) {
        User authorized_user = userService.getAuthorizedUser();
        Cart cart = cartRepository.findById(authorized_user.getId()).orElseThrow();
        Map<Product, Integer> userCart = cart.getCart();

        model.addAttribute("totalPrice", cart.getSumOfAllItems());
        model.addAttribute("cart", userCart);
        return "cart";
    }
}
