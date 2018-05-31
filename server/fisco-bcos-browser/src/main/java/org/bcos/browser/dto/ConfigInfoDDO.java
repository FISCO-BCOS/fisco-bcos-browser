package org.bcos.browser.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取配置信息
 */
@Component
public class ConfigInfoDDO {
	@Value("${node.url}")
	private String url;        //节点RPC地址

	public String getUrl() {
		return url;
	}
	
}
