package com.example.carpark.web;

import com.example.carpark.dto.LoginDTO;
import com.example.carpark.entity.User;
import com.example.carpark.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController extends BaseController<User, UserService>{

    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("loginDTO", new LoginDTO());

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@RequestBody LoginDTO login) {


        return "login";
    }
}
