package com.niuchaoqun.springboot.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NginxLog {
    private String time_local;

    private String remote_addr;

    private GeoipBean geoip;

    private String scheme;

    private String host;

    private String request_method;

    private String request_uri;

    private Integer status;

    private String cache_status;

    private Double request_time;

    private Long request_length;

    private Long bytes_sent;

    private String http_referer;

    private String http_user_agent;

    private UserAgentBean user_agent;

    private String http_x_requested_with;

    private String http_x_forwarded_for;

    private String ccprotect;

    private String http_cookie;

    private Integer cluster_id;

    private Integer node_id;

    private String node_uuid;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoipBean {

        private String country;
        private String province;
        private String city;
        private String telecom;

        private Double longitude;

        private Double latitude;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserAgentBean {

        private String device_type;

        private String device_version;

        private String os_type;

        private String os_version;

        private String browser_type;

        private String browser_version;
    }
}
