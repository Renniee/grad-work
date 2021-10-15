package com.example.carpark.web;

import com.example.carpark.dto.LoginDTO;
import com.example.carpark.dto.RegisterDTO;
import com.example.carpark.entity.RoleEntity;
import com.example.carpark.entity.RoleEnumType;
import com.example.carpark.entity.UserEntity;
import com.example.carpark.model.CurrentUser;
import com.example.carpark.service.impl.UserRoleService;
import com.example.carpark.service.impl.UserService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerDTO")) {
            model.addAttribute("registerDTO", new RegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(
            @Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        boolean isEqualPasswords = registerDTO.getPassword().equals(registerDTO.getConfirmPassword());
        if (bindingResult.hasErrors() || !isEqualPasswords) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.registerDTO",
                            bindingResult);
            return "register";
        }
        if (userService.isUserExists(registerDTO.getUsername())) {
            bindingResult.rejectValue
                    ("username", "error.username", "An account with this username already exists.");
            return "register";
        }

        UserEntity userEntity = this.modelMapper.map(registerDTO, UserEntity.class);
        RoleEntity role = new RoleEntity();
        try {
            role = userRoleService.getByName(RoleEnumType.USER);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        userEntity.setRoles(List.of(role));
        this.userService.create(userEntity);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new LoginDTO());
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);

            return "redirect:login";
        }

        if (userService.isLoginValid(loginDTO.getUsername(),
                loginDTO.getPassword())) {
            // user successfully logged in
            userService.loginUser(loginDTO.getUsername());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("notFound", true);

            return "redirect:login";
        }
    }

    //TODO: can change httpSession into harder code
    @GetMapping("/logout")
    public String logout() {
        currentUser.setAnonymous(true);
        return "redirect:/";
    }

    @GetMapping("/profile")
    String getFullStatistics(Model model, @AuthenticationPrincipal Principal principal) throws NotFoundException {
        model.addAttribute("username", principal.getName());
        model.addAttribute("user", this.userService.getByName(principal.getName()));
        return "profile";
    }

}