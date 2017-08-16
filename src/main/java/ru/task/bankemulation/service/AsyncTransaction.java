package ru.task.bankemulation.service;

import ru.task.bankemulation.entity.Transaction;

import java.util.concurrent.Callable;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

public class AsyncTransaction implements Callable<Transaction> {

    Transaction transaction;

    private TransactionService transactionService;

    public AsyncTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Transaction call() throws Exception {
        return process(transaction);
    }

    public Transaction process(Transaction transaction) {
        return transactionService.process(transaction);
    }

}
