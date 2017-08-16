package ru.task.bankemulation.repository;

import ru.task.bankemulation.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {}
