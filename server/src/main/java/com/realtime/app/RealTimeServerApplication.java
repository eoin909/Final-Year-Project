package com.realtime.app;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RealTimeServerApplication {

    @Value("${HOST:localhost}")
    private String host;

    @Value("${PORT:8000}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(RealTimeServerApplication.class, args);
    }
}