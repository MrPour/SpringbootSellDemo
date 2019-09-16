package com.chenhao.sell;

//一个测试竟然涉及到三个package
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sl4j 使用lombok可以省略成员变量的创建
public class LogTest {

    //针对当前类的日志
    private final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public  void test()
    {
        String name = "admin";
        String password = "123";
        //默认级别是info，所以低级别的debug不打出，而级别最高的error打出了
        logger.debug("debug..");
        //使用{}作为占位符
        logger.info("name:{},password:{}",name,password);
        logger.error("error..");

    }
}
