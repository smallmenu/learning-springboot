package com.niuchaoqun.springboot;

import com.niuchaoqun.springboot.config.MysqlProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {

    @Autowired
    private MysqlProperties mysqlProperties;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getMysql() {
        Assert.assertEquals(mysqlProperties.getHost(), "dev");
        Assert.assertEquals(mysqlProperties.getUser(), "dev_user");
        Assert.assertEquals(mysqlProperties.getMix(), "dev/dev_user");
    }

}
