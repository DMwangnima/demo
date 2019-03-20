package com.nuofankj.springdemo;

import com.nuofankj.springdemo.anno.Static;
import com.nuofankj.springdemo.common.StorageLong;
import com.nuofankj.springdemo.resource.CommonResource;
import com.nuofankj.springdemo.resource.ConfigValue;
import com.nuofankj.springdemo.resource.ConfigValueType;
import com.nuofankj.springdemo.resource.TestResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class SpringDemoApplicationTests {

    @Static
    private StorageLong<TestResource> testResources;
    @Static
    private StorageLong<CommonResource> commonnResources;
    @Static("1")
    private ConfigValue<String> configValue;

    @Test
    public void contextLoads() {
        System.out.println("===============转换后的数据=============");
        for (TestResource testResource : testResources.getAll()) {
            System.out.println(testResource);
        }
        for (CommonResource commonResource : commonnResources.getAll()) {
            System.out.println(commonResource);
        }

        System.out.println(configValue);
    }
}
