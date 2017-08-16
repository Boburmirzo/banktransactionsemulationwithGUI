package ru.task.bankemulation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.entity.Transaction;
import ru.task.bankemulation.service.AccountService;
import ru.task.bankemulation.service.TransactionService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Created by bumurzaqov on 08/14/2017.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
        Collection<Transaction> t = transactionService.findAll();
        List<Transaction> list = new ArrayList<>(t);
        Account account = accountService.findOne(100000);
        model.addAttribute("primaryAccount", account);
        model.addAttribute("primaryTransactionList", list);

		return "primaryAccount";
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        Account account = accountService.findOne(100000);
        account.setBalance(account.getBalance()+Double.parseDouble(amount));
        accountService.update(account);
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        Account account = accountService.findOne(100000);
        account.setBalance(account.getBalance()- Double.parseDouble(amount));
        accountService.update(account);
        return "redirect:/userFront";
    }
}
