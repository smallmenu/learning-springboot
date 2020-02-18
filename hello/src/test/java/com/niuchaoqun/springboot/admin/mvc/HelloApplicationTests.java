package com.niuchaoqun.springboot.admin.mvc;

import com.niuchaoqun.springboot.hello.property.MysqlProperty;
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
    private MysqlProperty mysqlProperty;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getMysql() {
        Assert.assertEquals(mysqlProperty.getHost(), "dev");
        Assert.assertEquals(mysqlProperty.getUser(), "dev_user");
        Assert.assertEquals(mysqlProperty.getMix(), "dev/dev_user");
    }

}
