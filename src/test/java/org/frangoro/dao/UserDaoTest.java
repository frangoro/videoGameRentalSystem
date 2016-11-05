package org.frangoro.dao;

import org.frangoro.config.DatabaseConfigTest;
import org.frangoro.dao.impl.UserDaoHbn;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DatabaseConfigTest.class, UserDaoHbn.class})
public class UserDaoTest {

}
