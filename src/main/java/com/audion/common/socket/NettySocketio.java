//package com.audion.common.socket;
//
//import com.audion.dto.SocketMsgDto;
//import com.corundumstudio.socketio.SocketIONamespace;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DataListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class NettySocketio {
//
//    private final SocketIONamespace namespace;
//
//    @Autowired
//    public NettySocketio(SocketIOServer server){
//        this.namespace = server.addNamespace("/socketio");
////        this.namespace.addConnectListener(onConnected());
////        this.namespace.addDisconnectListener(onDisconnected());
//        this.namespace.addEventListener("stt", SocketMsgDto.class, onReceived());
//    }
//
//    private DataListener<SocketMsgDto> onReceived() {
//        return (client, data, ackSender) -> {
//            log.info("NettySocketio]-[onReceived]-[{}] Received message '{}'", client.getSessionId().toString(), data);
//            namespace.getBroadcastOperations().sendEvent("stt", data);
//        };
//    }
//
////    private ConnectListener onConnected() {
////        return client -> {
////            HandshakeData handshakeData = client.getHandshakeData();
////            log.info("[NettySocketio]-[onConnected]-[{}] Connected to Socket Module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
////        }
////    }
//}
