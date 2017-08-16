package ru.task.bankemulation.ws;

import ru.task.bankemulation.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bumurzaqov on 08/14/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractTest {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

}
