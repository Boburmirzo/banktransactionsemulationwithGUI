package ru.task.bankemulation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.service.AccountService;

import java.security.Principal;
/**
 * Created by bumurzaqov on 08/14/2017.
 */
@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;


    @RequestMapping("/")
    public String home() {
        return "redirect:/userFront";
    }

    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        Account account = accountService.findOne(100000);

        model.addAttribute("primaryAccount", account);

        return "userFront";
    }
}
