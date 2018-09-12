/**
 * Copyright (c) 2018, Mr.Wang (recallcode@aliyun.com) All rights reserved.
 */

package com.youxuepai.mqtt.server.auth.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.youxuepai.mqtt.server.common.auth.IAuthService;
import java.security.interfaces.RSAPrivateKey;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * 用户名和密码认证服务
 */
@Service
public class AuthService implements IAuthService {

	private RSAPrivateKey privateKey;

	@Override
	public boolean checkValid(String username, String password) {
		if (StrUtil.isBlank(username)) return false;
		if (StrUtil.isBlank(password)) return false;
		RSA rsa = new RSA(privateKey, null);
		String value = rsa.encryptBcd(username, KeyType.PrivateKey);
		return value.equals(password) ? true : false;
	}

	@PostConstruct
	public void init() {
		privateKey = IoUtil.readObj(AuthService.class.getClassLoader().getResourceAsStream("keystore/auth-private.key"));
	}

}
