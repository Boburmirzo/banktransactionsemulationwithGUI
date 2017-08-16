package ru.task.bankemulation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.entity.Transaction;
import ru.task.bankemulation.service.AccountService;
import ru.task.bankemulation.service.AsyncTransaction;
import ru.task.bankemulation.service.TransactionService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ThreadPoolTaskExecutor transactionPool;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String betweenAccounts(Model model) {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

//    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
//    public String betweenAccountsPost(
//            @ModelAttribute("transferFrom") String transferFrom,
//            @ModelAttribute("transferTo") String transferTo,
//            @ModelAttribute("amount") String amount,
//            Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        PrimaryAccount primaryAccount = user.getPrimaryAccount();
//        SavingsAccount savingsAccount = user.getSavingsAccount();
//        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
//
//        return "redirect:/userFront";
//    }

    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String recipient(Model model, Principal principal) {
        Collection<Account> accounts = accountService.findAll();
        List<Account> list = new ArrayList<>(accounts);
        Account newAccount = new Account();

        model.addAttribute("recipientList", list);
        model.addAttribute("recipient", newAccount);

        return "recipient";
    }

    @RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
    public String recipientPost(@ModelAttribute("recipient") Account account, Principal principal) {

        Account ac = accountService.create(account);

        return "redirect:/transfer/recipient";
    }


    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal) {
        Collection<Account> accounts = accountService.findAll();
        List<Account> list = new ArrayList<>(accounts);
        model.addAttribute("recipientList", list);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse",method = RequestMethod.POST)
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
        AsyncTransaction asyncTransaction;
        Future<Transaction> asyncResponse;
        Account accountSender = accountService.findOne(100000);
        Account accountReceiver = accountService.findOne(Long.valueOf(recipientName));
        accountSender.setBalance(accountSender.getBalance() - Double.parseDouble(amount));
        accountReceiver.setBalance(accountReceiver.getBalance() + Double.parseDouble(amount));
        accountService.update(accountSender);
        accountService.update(accountReceiver);
        Transaction newTransaction = new Transaction(accountSender.getId(),accountReceiver.getId(),Double.valueOf(amount));
        try {
            asyncTransaction = new AsyncTransaction(newTransaction);
            asyncTransaction.setTransactionService(transactionService);
            asyncResponse = transactionPool.submit(asyncTransaction);
        } catch (Exception e) {
            System.out.println("Error is accuried");
        }
        return "redirect:/userFront";
    }
}
