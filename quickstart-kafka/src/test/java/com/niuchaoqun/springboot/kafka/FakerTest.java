package com.niuchaoqun.springboot.kafka;

import com.github.javafaker.Faker;
import com.niuchaoqun.springboot.kafka.sender.ProducerMessage;
import org.junit.Test;

public class FakerTest {

    @Test
    public void message() {
        String log = ProducerMessage.log();
        System.out.println(log);
    }

    @Test
    public void test() {
        Faker faker = new Faker();
        System.out.println(faker.address().cityName());
        System.out.println(faker.address().latitude());
        System.out.println(faker.address().longitude());
        System.out.println(faker.internet().ipV4Address());
        System.out.println(faker.internet().userAgentAny());
        System.out.println(faker.internet().domainName());
        System.out.println(faker.internet().url());
        System.out.println(faker.app().version());
        System.out.println(faker.company().logo());
        System.out.println(faker.file().fileName());
    }
}
