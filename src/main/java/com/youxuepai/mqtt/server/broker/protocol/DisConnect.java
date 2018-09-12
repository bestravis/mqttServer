/**
 * Copyright (c) 2018, Mr.Wang (recallcode@aliyun.com) All rights reserved.
 */

package com.youxuepai.mqtt.server.broker.protocol;

import com.alibaba.fastjson.JSONObject;
import com.youxuepai.mqtt.server.common.message.IDupPubRelMessageStoreService;
import com.youxuepai.mqtt.server.common.message.IDupPublishMessageStoreService;
import com.youxuepai.mqtt.server.common.session.ISessionStoreService;
import com.youxuepai.mqtt.server.common.session.SessionStore;
import com.youxuepai.mqtt.server.common.subscribe.ISubscribeStoreService;
import com.youxuepai.mqtt.server.common.subscribe.SubscribeStore;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageFactory;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.AttributeKey;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * DISCONNECT连接处理
 */
@Component
public class DisConnect {

	private static final Logger LOGGER = LoggerFactory.getLogger(DisConnect.class);

	private ISessionStoreService sessionStoreService;

	private ISubscribeStoreService subscribeStoreService;

	private IDupPublishMessageStoreService dupPublishMessageStoreService;

	private IDupPubRelMessageStoreService dupPubRelMessageStoreService;

	public DisConnect(ISessionStoreService sessionStoreService, ISubscribeStoreService subscribeStoreService, IDupPublishMessageStoreService dupPublishMessageStoreService, IDupPubRelMessageStoreService dupPubRelMessageStoreService) {
		this.sessionStoreService = sessionStoreService;
		this.subscribeStoreService = subscribeStoreService;
		this.dupPublishMessageStoreService = dupPublishMessageStoreService;
		this.dupPubRelMessageStoreService = dupPubRelMessageStoreService;
	}

	public void processDisConnect(Channel channel, MqttMessage msg) {
		String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
		SessionStore sessionStore = sessionStoreService.get(clientId);
		if (sessionStore.isCleanSession()) {
			subscribeStoreService.removeForClient(clientId);
			dupPublishMessageStoreService.removeByClient(clientId);
			dupPubRelMessageStoreService.removeByClient(clientId);
		}
		LOGGER.debug("DISCONNECT - clientId: {}, cleanSession: {}", clientId, sessionStore.isCleanSession());
		sessionStoreService.remove(clientId);
		if(clientId != null && Pattern.matches("stu_.*",clientId)){
			List<SubscribeStore> subscribeStores = subscribeStoreService.search("/sys/offline");
			subscribeStores.forEach(subscribeStore->{
				if (sessionStoreService.containsKey(subscribeStore.getClientId())) {
					// 订阅者收到MQTT消息的QoS级别, 最终取决于发布消息的QoS和主题订阅的QoS
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId",clientId);
					jsonObject.put("status",0);
					jsonObject.put("clintIds",sessionStoreService.getAll());
					MqttPublishMessage publishMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
							new MqttFixedHeader(MqttMessageType.PUBLISH, true, MqttQoS.AT_MOST_ONCE, false, 0),
							new MqttPublishVariableHeader("/sys/offline", 0), Unpooled.buffer().writeBytes(jsonObject.toJSONString().getBytes()));
					LOGGER.debug("PUBLISH - clientId: {}, topic: {}, Qos: {}", subscribeStore.getClientId(), "/sys/offline", 0);
					sessionStoreService.get(subscribeStore.getClientId()).getChannel().writeAndFlush(publishMessage);
				}
			});
		}
		channel.close();
	}

}
