package ru.task.bankemulation.util;

import ru.task.bankemulation.entity.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
public class TransactionLogger {

    private static final Logger logger = LogManager.getLogger("transaction");

    // Log de las transacciones procesadas
    public static void log(Transaction transaction){
        logger.info("Transaction id: {}, status: {}, Amount: {}, Source account: {}, " +
                        "Target Account: {}, Tax: {}, Type: {}.",
                transaction.getId(),
                transaction.getStatus(),
                transaction.getAmount(),
                transaction.getSource(),
                transaction.getTarget(),
                transaction.getTax(),
                transaction.getType());
    }
}
