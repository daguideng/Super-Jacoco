package com.xiaoju.basetech.config;

import com.xiaoju.basetech.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * @description: 路径配置类
 * @author: assistant
 * @time: 2025/12/27
 */
@Configuration
public class PathConfig {

    @Value("${project.code.root}")
    private String codeRoot;

    @Value("${project.log.path}")
    private String logPath;

    @Value("${project.report.path}")
    private String reportPath;

    @Value("${project.jacoco.resource.path}")
    private String jacocoResourcePath;

    @PostConstruct
    public void init() {
        // 初始化Constants类中的路径配置
        Constants.CODE_ROOT = codeRoot;
        Constants.LOG_PATH = logPath;
        Constants.REPORT_PATH = reportPath;
        Constants.JACOCO_RESOURE_PATH = jacocoResourcePath;
        
        System.out.println("路径配置初始化完成:");
        System.out.println("CODE_ROOT: " + Constants.CODE_ROOT);
        System.out.println("LOG_PATH: " + Constants.LOG_PATH);
        System.out.println("REPORT_PATH: " + Constants.REPORT_PATH);
        System.out.println("JACOCO_RESOURE_PATH: " + Constants.JACOCO_RESOURE_PATH);
    }
}