package com.xiaoju.basetech.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * JaCoCo配置类
 */
@Configuration
@Data
public class JacocoConfig {
    
    @Value("${jacoco.cli.version:0.8.11}")
    private String version;
    
    /**
     * 获取JaCoCo插件的完整坐标
     */
    public String getJacocoPluginCoordinate() {
        return "org.jacoco:jacoco-maven-plugin:" + version;
    }
    
    /**
     * 获取prepare-agent命令
     */
    public String getPrepareAgentCommand() {
        return getJacocoPluginCoordinate() + ":prepare-agent";
    }
    
    /**
     * 获取report命令
     */
    public String getReportCommand() {
        return getJacocoPluginCoordinate() + ":report";
    }
}