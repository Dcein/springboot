package com.example.demo;

import dcein.springboot.demo.DemoApplication;
import dcein.springboot.demo.rabbitMQ.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private Sender sender;

	@Test
    public void hello() throws Exception {
        sender.send();
    }

}
