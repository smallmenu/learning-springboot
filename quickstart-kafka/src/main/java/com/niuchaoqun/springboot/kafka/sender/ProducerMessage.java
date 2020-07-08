package com.niuchaoqun.springboot.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.niuchaoqun.springboot.kafka.dto.NginxLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.UUID;

import static com.github.suosi.commons.helper.Static.date;
import static com.github.suosi.commons.helper.Static.md5;

@Slf4j
public class ProducerMessage {
    private final static SecureRandom secureRandom = new SecureRandom();

    public static final Faker faker = new Faker();

    public static String log() {
        String message = null;

        NginxLog.GeoipBean geoipBean = NginxLog.GeoipBean.builder()
                .country("china")
                .province(province())
                .city(faker.country().capital())
                .telecom(telecom())
                .longitude(Double.parseDouble(faker.address().longitude()))
                .latitude(Double.parseDouble(faker.address().latitude()))
                .build();
        NginxLog.UserAgentBean userAgentBean = NginxLog.UserAgentBean.builder()
                .device_type(device_type())
                .device_version(faker.app().version())
                .os_type(os_type())
                .os_version(faker.app().version())
                .browser_type(browser_type())
                .browser_version(faker.app().version())
                .build();
        NginxLog nginxLog = NginxLog.builder().geoip(geoipBean).user_agent(userAgentBean)
                .time_local(date())
                .remote_addr(faker.internet().ipV4Address())
                .scheme("https")
                .host(faker.internet().url())
                .request_method(request_method())
                .request_uri("/" + StringUtils.replace(faker.file().fileName(), "\\", "/"))
                .status(status())
                .cache_status(cache_status())
                .request_time(faker.number().randomDouble(6, 0L, 5L))
                .request_length(faker.number().numberBetween(64L, 1024L))
                .bytes_sent(faker.number().numberBetween(256L, 2048000L))
                .http_referer(faker.internet().url())
                .http_user_agent(faker.internet().userAgentAny())
                .http_x_requested_with("-")
                .http_x_forwarded_for("-")
                .ccprotect("")
                .http_cookie("MFSHIROSID=11d1c6c7-1370-418d-8a23-d62054a336e0; JSESSIONID=0CC4249ABA03B2409A6B69A3A5A1CACC")
                .cluster_id(secureRandom.nextInt(50))
                .node_id(secureRandom.nextInt(1000))
                .node_uuid(md5(String.valueOf(secureRandom.nextInt(1000))))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.writeValueAsString(nginxLog);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return message;
    }

    private static String request_method() {
        String[] datas = {"GET", "GET", "GET", "POST"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static int status() {
        int[] datas = {200, 200, 200, 200, 200, 200, 200, 200, 404, 403};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String cache_status() {
        String[] datas = {"HIT", "HIT", "MISS"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String province() {
        String[] datas = {"beijing", "shanghai", "guangdong", "anhui", "shandong", "jiangsu", "zhejiang", "hubei", "beijing", "beijing", "shanghai", "henan", "hunan", "xizang", "xinjiang"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String os_type() {
        String[] datas = {"windows", "windows", "windows", "windows", "windows", "macos", "linux"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String browser_type() {
        String[] datas = {"chrome", "chrome", "chrome", "chrome", "qqbrowser", "firefox", "ie"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String device_type() {
        String[] datas = {"iPhone", "Android", "Android", "Android"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }

    private static String telecom() {
        String[] datas = {"中国电信", "中国电信", "中国电信", "中国联通", "中国移动"};
        int index = secureRandom.nextInt(datas.length);
        return datas[index];
    }
}
