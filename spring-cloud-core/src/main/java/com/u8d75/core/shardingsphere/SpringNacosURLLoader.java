package com.u8d75.core.shardingsphere;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.u8d75.core.exception.BizException;
import com.u8d75.core.spring.SpringContextHolder;
import lombok.SneakyThrows;
import org.apache.shardingsphere.infra.url.spi.ShardingSphereURLLoader;

import java.util.Properties;

/**
 * SpringNacosURLLoader
 */
public final class SpringNacosURLLoader implements ShardingSphereURLLoader {

    private static final Long TIMEOUT = 3000L;

    @Override
    @SneakyThrows(BizException.class)
    public String load(final String configurationSubject, final Properties queryProps) {
        NacosConfigManager nacosConfigManager = SpringContextHolder.getApplicationContext().getBean(NacosConfigManager.class);
        try {
            String content = nacosConfigManager.getConfigService().getConfig(configurationSubject, nacosConfigManager.getNacosConfigProperties().getGroup(), TIMEOUT);
            return content.replace("\n", System.lineSeparator()).replace("#", "");
        } catch (NacosException e) {
            throw new BizException(e);
        }
    }

    @Override
    public String getType() {
        return "nacos:";
    }

}