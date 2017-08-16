package ru.task.bankemulation.entity;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
public enum TransactionStatus {
    SUCCESS,
    FAILURE_SAME_ACCOUNT,
    FAILURE_ACCOUNT_NOT_FOUND,
    FAILURE_INSUFFICIENT_BALANCE
}