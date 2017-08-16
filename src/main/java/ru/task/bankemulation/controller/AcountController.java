package ru.task.bankemulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.service.AccountService;

import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@RestController
@RequestMapping("/api/account")
public class AcountController extends BaseController{

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Account>> getAllAccounts(){
        Collection<Account> accounts = accountService.findAll();
        if (accounts.isEmpty()){
            throw new NoResultException("Not accounts found");
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id){
        Account account = accountService.findOne(id);
        if (account == null){
            throw new NoResultException("Account " + id + " not found");
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> insert(@RequestBody Account account){
        Account ac = accountService.create(account);
        if (ac == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ac, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> update(@RequestBody Account account){
        Account a = accountService.findOne(account.getId());
        if (a == null){
            throw new NoResultException("Account " + account.getId() + " not found");
        }
        Account ac = accountService.update(account);
        if (ac == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ac, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> delete(@PathVariable("id") long id){
        Account a = accountService.findOne(id);
        if (a == null){
            throw new NoResultException("Account " + id + " not found");
        }
        accountService.delete(id);
        return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
    }
}
