package com.niuchaoqun.springboot.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuchaoqun.springboot.kafka.dto.NginxLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.github.suosi.commons.helper.Static.date;

@Service
@Slf4j
public class Producer {
    private static final String TOPIC = "test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        NginxLog.GeoipBean geoipBean = NginxLog.GeoipBean.builder()
                .country("china")
                .province("beijing")
                .city("beijing")
                .telecom("中国电信")
                .longitude(31.224349)
                .latitude(121.476753)
                .build();
        NginxLog.UserAgentBean userAgentBean = NginxLog.UserAgentBean.builder()
                .device_type("iPhone")
                .device_version("13.5")
                .os_type("windows")
                .os_version("10.0")
                .browser_type("qqbrowser")
                .browser_version("10.1")
                .build();
        NginxLog nginxLog = NginxLog.builder().geoip(geoipBean).user_agent(userAgentBean)
                .time_local(date())
                .remote_addr("116.252.238.67")
                .scheme("https")
                .host("www.ylmfu.com")
                .request_method("GET")
                .request_uri("/aaa")
                .status(200)
                .cache_status("HIT")
                .request_time("0.000")
                .request_length(313333L)
                .bytes_sent(132123L)
                .http_referer("http://www.ylmfu.com/upan/html/list4-1.html")
                .http_user_agent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)")
                .http_x_requested_with("-")
                .http_x_forwarded_for("-")
                .ccprotect("")
                .http_cookie("-")
                .cluster_id(1)
                .node_id(1)
                .node_uuid("xxx-xxx-xxx-xxx")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(nginxLog);

            log.info(message);

            log.info(String.valueOf(message.length()));

            //kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
