package com.example.store.Controllers;

import com.example.store.Models.User;
import com.example.store.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public String userList(Model model) {
    Iterable<User> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "userList";
  }

  @GetMapping("{user}")
  public String userEditForm(@PathVariable User user, Model model) {
    model.addAttribute("user", user);
    return "userEdit";
  }
}
