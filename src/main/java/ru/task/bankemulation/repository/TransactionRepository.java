package ru.task.bankemulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.bankemulation.entity.Transaction;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}