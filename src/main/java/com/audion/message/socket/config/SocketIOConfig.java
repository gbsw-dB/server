package com.audion.message.socket.config;

import com.audion.common.environment.ConfigUtil;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {
    @Bean
    public SocketIOServer socketIOServer(ConfigUtil configUtil) {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(configUtil.getProperty("socketio.host"));
        config.setPort(Integer.parseInt(configUtil.getProperty("socketio.port")));
        config.setTransports(Transport.WEBSOCKET, Transport.POLLING);

        return new SocketIOServer(config);
    }
}
