package fudan.pbl.mm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //允许客户端使用socketJs方式访问，访问点为ws，允许跨域
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        //socket = new SockJS(rootPath + '/ws');
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //订阅广播 Broker（消息代理）名称
        registry.enableSimpleBroker("/topic", "/user"); // Enables a simple in-memory broker
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app/");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/user/");
    }
}