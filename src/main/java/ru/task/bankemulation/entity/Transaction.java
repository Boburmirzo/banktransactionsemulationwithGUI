package ru.task.bankemulation.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    private long source;
    private long target;
    private double amount;
    private TransactionType type;
    private double tax;
    private TransactionStatus status;

    public Transaction() {
    }

    public Transaction(long source, long target, double amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;

    }

    public long getId() {
        return id;
    }

    public long getSource() {
        return source;
    }

    public long getTarget() {
        return target;
    }

    public double getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}