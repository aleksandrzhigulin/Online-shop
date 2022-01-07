package com.example.store.Controllers;

import com.example.store.Models.Role;
import com.example.store.Models.User;
import com.example.store.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/registration")
  public String registration(Model model) {
    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(User user, Model model) {
    User dbUser = userRepository.findByUsername(user.getUsername());

    if (dbUser != null) {
      model.addAttribute("message", "UserExists");
      return "registration";
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);
    return "redirect:/login";
  }
}
