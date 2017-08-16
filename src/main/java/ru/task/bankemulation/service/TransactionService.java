package ru.task.bankemulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.task.bankemulation.entity.Account;
import ru.task.bankemulation.entity.Transaction;
import ru.task.bankemulation.entity.TransactionStatus;
import ru.task.bankemulation.entity.TransactionType;
import ru.task.bankemulation.repository.AccountRepository;
import ru.task.bankemulation.repository.TransactionRepository;
import ru.task.bankemulation.util.TransactionLogger;

import java.util.Collection;

import static ru.task.bankemulation.util.Constants.*;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Collection<Transaction> findAll(){
        return transactionRepository.findAll();
    }


    public Transaction find(long id){
        return transactionRepository.findOne(id);
    }

    public Transaction process(Transaction transaction) {

        TransactionType type = getType(transaction);
        transaction.setType(type);

        double tax = computeTax(transaction);
        transaction.setTax(tax);

        TransactionStatus status = computeStatus(transaction);
        transaction.setStatus(status);

        if (status == TransactionStatus.SUCCESS){
            save(transaction);
        }
        TransactionLogger.log(transaction);
        return transaction;
    }

    public Transaction save(Transaction transaction){
        Account source = accountRepository.findOne(transaction.getSource());
        Account target = accountRepository.findOne(transaction.getTarget());

        double totalAmount = transaction.getAmount() + transaction.getTax();
        double currentSourceBalance = source.getBalance();
        double currentTargetBalance = target.getBalance();

        source.setBalance(currentSourceBalance - totalAmount);
        target.setBalance(currentTargetBalance + totalAmount);
        accountRepository.save(source);
        accountRepository.save(target);

        return transactionRepository.save(transaction);
    }

    public TransactionStatus computeStatus(Transaction transaction) {
        if (transaction.getSource() == transaction.getTarget())
            return TransactionStatus.FAILURE_SAME_ACCOUNT;

        Account source = accountRepository.findOne(transaction.getSource());
        if (source == null)
            return TransactionStatus.FAILURE_ACCOUNT_NOT_FOUND;

        Account target = accountRepository.findOne(transaction.getTarget());
        if (target == null)
            return TransactionStatus.FAILURE_ACCOUNT_NOT_FOUND;

        double resultSourceBalance = source.getBalance() - transaction.getAmount() - transaction.getTax();
        if (resultSourceBalance < 0)
            return TransactionStatus.FAILURE_INSUFFICIENT_BALANCE;

        return TransactionStatus.SUCCESS;
    }


    public double computeTax(Transaction transaction) {
        Account source = accountRepository.findOne(transaction.getSource());
        Account target = accountRepository.findOne(transaction.getTarget());

        if (transaction.getType() == TransactionType.INTERNATIONAL) {
            return transaction.getAmount() * INTERNATIONAL_TAX;
        } else if (source.getBank().equalsIgnoreCase(target.getBank())) {
            return transaction.getAmount() * SAME_BANK_TAX;
        } else {
            return transaction.getAmount() * NATIONAL_TAX;
        }
    }

    public TransactionType getType(Transaction transaction){

        Account source = accountRepository.findOne(transaction.getSource());
        Account target = accountRepository.findOne(transaction.getTarget());

        return source.getCountry().equalsIgnoreCase(target.getCountry()) ?
                TransactionType.NATIONAL : TransactionType.INTERNATIONAL;
    }

}
