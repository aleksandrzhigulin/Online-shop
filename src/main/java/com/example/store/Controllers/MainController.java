package com.example.store.Controllers;

import com.example.store.Models.Product;
import com.example.store.Models.User;
import com.example.store.Repositories.CartRepository;
import com.example.store.Repositories.ProductRepository;
import com.example.store.Services.UserService;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CartRepository cartRepository;

  @Value("${upload.path}")
  private String uploadPath;

  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("title", "Main Page");
    // Get all products
    System.out.println(userService.getAuthorizedUser().getRoles());
    Iterable<Product> products = productRepository.findAll();
    model.addAttribute("products", products);

    System.out.println("All carts: " + cartRepository.findAll());
    return "main";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/products/add")
  public String addProduct(Model model) {
    return "productAdd";
  }

  @PostMapping("/products/add")
  // Название параметра из атрибута name
  public String productPostAdd(@RequestParam String name, @RequestParam Integer price,
      @RequestParam String description, Model model,
      @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file)
      throws IOException {

    Product product = new Product(name, description, price);

    if (file != null && !file.getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      // Generate unique name to file
      String fileUUID = UUID.randomUUID().toString();
      String filename = fileUUID + '.' + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "/" + filename));

      product.setImageFileName(filename);
    }

    productRepository.save(product);
    return "redirect:/home";
  }

  @GetMapping("/products/{id}")
  public String productDetail(@PathVariable(value = "id") long productId, Model model) {
    if (productRepository.existsById(productId)) {
      Optional<Product> product = productRepository.findById(productId);
      // Convert to ArrayList
      List<Product> result = new ArrayList<>();
      product.ifPresent(result::add);
      model.addAttribute("products", result);
      return "productDetail";
    } else {
      return "redirect:/home";
    }
  }


  @GetMapping("/products/{id}/edit")
  public String productEdit(@PathVariable(value = "id") long productId, Model model) {
    if (productRepository.existsById(productId)) {
      Optional<Product> product = productRepository.findById(productId);
      // Convert to ArrayList
      List<Product> result = new ArrayList<>();
      product.ifPresent(result::add);
      model.addAttribute("products", result);
      return "productEdit";
    } else {
      return "redirect:/home";
    }
  }

  @PostMapping("/products/{id}/edit")
  public String productPostEdit(@PathVariable("id") long productId, Model model,
      @RequestParam Integer price, @RequestParam String name,
      @RequestParam String description) {
    Product product = productRepository.findById(productId).orElseThrow();
    product.setName(name);
    product.setPrice(price);
    product.setDescription(description);
    productRepository.save(product);
    return "redirect:/products/{id}";
  }


  @PostMapping("/products/{id}/delete")
  public String productPostDelete(@PathVariable("id") long productId, Model model) {
    Product product = productRepository.findById(productId).orElseThrow();
    productRepository.delete(product);
    return "redirect:/products/{id}";
  }

  @GetMapping("/")
  public String root(Model model) {
    return "main";
  }
}
