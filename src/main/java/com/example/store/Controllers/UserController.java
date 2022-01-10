package com.example.store.Controllers;

import com.example.store.Models.Role;
import com.example.store.Models.User;
import com.example.store.Repositories.UserRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/user")
  public String userList(Model model) {
    Iterable<User> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "userList";
  }

  @GetMapping("/user/{user}")
  public String userEditForm(@PathVariable User user, Model model) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "userEdit";
  }

  @PostMapping("/user")
  public String userSave(@RequestParam("id") User user, @RequestParam String username,
      @RequestParam Map<String, String> form) {
    System.out.println(form);
    Set<String> roles = Arrays.stream(Role.values())
        .map(Role::name)
        .collect(Collectors.toSet());

    user.getRoles().clear();
    for (String key : form.keySet()) {
      if (roles.contains(key)) {
        user.getRoles().add(Role.valueOf(key));
      }
    }

    user.setUsername(username);
    userRepository.save(user);
    return "redirect:/user";
  }
}
