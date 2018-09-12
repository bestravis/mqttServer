/**
 * Copyright (c) 2018, Mr.Wang (recallcode@aliyun.com) All rights reserved.
 */

package com.youxuepai.mqtt.server.store.session;

import com.youxuepai.mqtt.server.common.session.ISessionStoreService;
import com.youxuepai.mqtt.server.common.session.SessionStore;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * 会话存储服务
 */
@Service
public class SessionStoreService implements ISessionStoreService {

	private Map<String, SessionStore> sessionCache = new ConcurrentHashMap<String, SessionStore>();

	@Override
	public void put(String clientId, SessionStore sessionStore) {
		sessionCache.put(clientId, sessionStore);
	}

	@Override
	public SessionStore get(String clientId) {
		return sessionCache.get(clientId);
	}

	@Override
	public boolean containsKey(String clientId) {
		return sessionCache.containsKey(clientId);
	}

	@Override
	public void remove(String clientId) {
		sessionCache.remove(clientId);
	}

	/**
	 * 获取所有的会话
	 */
	@Override
	public Set<String> getAll() {
		return sessionCache.keySet();
	}
}
