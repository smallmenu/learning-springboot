package com.niuchaoqun.springboot.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.niuchaoqun.springboot.kafka.dto.NginxLog;
import com.niuchaoqun.springboot.kafka.sender.ProducerMessage;
import org.junit.Test;

import java.io.IOException;

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

    @Test
    public void json() {
        String json = "{\"time_local\":\"2020-07-09 14:28:13\",\"remote_addr\":\"12.83.240.234\",\"geoip\":{\"country\":\"china\",\"province\":\"xizang\",\"city\":\"Ottawa\",\"telecom\":\"中国电信\",\"longitude\":47.739594,\"latitude\":-20.346129},\"scheme\":\"https\",\"host\":\"www.yi-schroeder.info\",\"request_method\":\"GET\",\"request_uri\":\"/et_blanditiis/quod.mp3\",\"status\":404,\"cache_status\":\"HIT\",\"request_time\":0.164185,\"request_length\":795,\"bytes_sent\":399723,\"http_referer\":\"www.naomi-gaylord.co\",\"http_user_agent\":\"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)\",\"user_agent\":{\"device_type\":\"Android\",\"device_version\":\"5.11\",\"os_type\":\"macos\",\"os_version\":\"8.4\",\"browser_type\":\"chrome\",\"browser_version\":\"7.3\"},\"http_x_requested_with\":\"-\",\"http_x_forwarded_for\":\"-\",\"ccprotect\":\"\",\"http_cookie\":\"MFSHIROSID=11d1c6c7-1370-418d-8a23-d62054a336e0; JSESSIONID=0CC4249ABA03B2409A6B69A3A5A1CACC\",\"cluster_id\":48,\"node_id\":518,\"node_uuid\":\"4c56ff4ce4aaf9573aa5dff913df997a\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            NginxLog nginxLog = objectMapper.readValue(json, NginxLog.class);
            System.out.println(nginxLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
