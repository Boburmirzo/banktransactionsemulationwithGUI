package ru.task.bankemulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import ru.task.bankemulation.entity.Transaction;
import ru.task.bankemulation.entity.TransactionStatus;
import ru.task.bankemulation.service.AsyncTransaction;
import ru.task.bankemulation.service.TransactionService;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController{


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ThreadPoolTaskExecutor transactionPool;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Transaction>> findAll(){
        Collection<Transaction> t = transactionService.findAll();
        if (t.isEmpty()){
            throw new NoResultException("Not transaction found");
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> find(@PathVariable("id") long id){
        Transaction t = transactionService.find(id);
        if (t == null){
            throw new NoResultException("Transaction " + id + " not found");
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> insert(@RequestBody Transaction transaction){
        AsyncTransaction asyncTransaction;
        Future<Transaction> asyncResponse;
        try {
            asyncTransaction = new AsyncTransaction(transaction);
            asyncTransaction.setTransactionService(transactionService);
            asyncResponse = transactionPool.submit(asyncTransaction);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            if (asyncResponse.get().getStatus() == TransactionStatus.FAILURE_ACCOUNT_NOT_FOUND){
                return new ResponseEntity<>(asyncResponse.get(), HttpStatus.NOT_FOUND);
            } else if (asyncResponse.get().getStatus() == TransactionStatus.FAILURE_SAME_ACCOUNT){
                return new ResponseEntity<>(asyncResponse.get(), HttpStatus.UNPROCESSABLE_ENTITY);
            } else if (asyncResponse.get().getStatus() == TransactionStatus.FAILURE_INSUFFICIENT_BALANCE){
                return new ResponseEntity<>(asyncResponse.get(), HttpStatus.UNPROCESSABLE_ENTITY);
            } else {
                return new ResponseEntity<>(asyncResponse.get(), HttpStatus.CREATED);
            }

        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
