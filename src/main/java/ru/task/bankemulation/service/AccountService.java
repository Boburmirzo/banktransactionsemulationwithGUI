package ru.task.bankemulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.repository.AccountRepository;

import java.util.Collection;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Collection<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account findOne(long id){
        return accountRepository.findOne(id);
    }

    public Account create(Account account){
        return accountRepository.save(account);
    }

    public Account update(Account account){
        return accountRepository.save(account);
    }

    public Account delete(long id){
        Account account = accountRepository.findOne(id);
        accountRepository.delete(id);
        return account;
    }

}
