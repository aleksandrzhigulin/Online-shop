package com.example.store.Controllers;

import com.example.store.Models.Role;
import com.example.store.Models.User;
import com.example.store.Repositories.UserRepository;
import com.example.store.Services.UserService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Value("${upload.path}")
  private String uploadPath;

  @Autowired
  private UserService userService;

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
      @RequestParam Map<String, String> form, @RequestParam("file")
      MultipartFile file) throws IOException {

    if (file != null && !file.getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      // Generate unique name to file
      String fileUUID = UUID.randomUUID().toString();
      String filename = fileUUID + '.' + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "/" + filename));

      user.setAvatarFileName(filename);
    }

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

  @PostMapping("/user/delete/{id}")
  public String deleteUserById(@PathVariable("id") User user) {
    userRepository.delete(user);
    return "redirect:/user";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/profile/edit")
  public String editProfile(Model model) {
    User user = userService.getAuthorizedUser();
    System.out.println(user);
    model.addAttribute("user", user);
    return "profileEdit";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/profile/edit")
  public String editPostProfile(Model model, @RequestParam String username,
      @RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
    User user = userRepository.findById(id).orElseThrow();
    user.setUsername(username);

    if (file != null && !file.getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      // Generate unique name to file
      String fileUUID = UUID.randomUUID().toString();
      String filename = fileUUID + '.' + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "/" + filename));

      user.setAvatarFileName(filename);
    }

    userRepository.save(user);

    return "redirect:/login?logout";

    }

}
