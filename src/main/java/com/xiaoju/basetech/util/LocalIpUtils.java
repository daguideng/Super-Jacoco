package com.xiaoju.basetech.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Component
public class LocalIpUtils {

    private static String serverPort;
    private static String hostIp;

    @Value("${server.port:8899}")
    public void setServerPort(String port) {
        serverPort = port;
    }

    @Value("${server.host}")
    public void setHostIp(String ip) {
        hostIp = ip;
    }

    public static String getTomcatBaseUrl() {
        try {
            String localIp;
            if (hostIp != null && !hostIp.trim().isEmpty()) {
                // 使用配置的host IP
                localIp = hostIp;
            } else {
                // 使用自动检测的IP
                localIp = GetIPAddress.getLinuxLocalIp();
            }
            return "http://" + localIp + ":" + serverPort + "/";
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
