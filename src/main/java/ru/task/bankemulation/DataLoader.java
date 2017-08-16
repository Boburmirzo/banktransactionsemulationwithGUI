package ru.task.bankemulation;

import ru.task.bankemulation.repository.AccountRepository;
import ru.task.bankemulation.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.task.bankemulation.repository.TransactionRepository;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
@Component
public class DataLoader implements ApplicationRunner {

    private AccountRepository accountRepository;
    @Autowired
    public DataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void run(ApplicationArguments args) {
        accountRepository.save(new Account("Sberbank", "Russia", 250000));
        accountRepository.save(new Account("Hamkorbank", "Uzbekistan", 14500.50));
        accountRepository.save(new Account("Tinkoff", "Russia", 33900));
        accountRepository.save(new Account("AKbars", "Russia", 15800250));
        accountRepository.save(new Account("Swiss", "Sweden", 8450.40));
    }
}