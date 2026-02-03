# 使用最小基础镜像，手动安装JDK 21
FROM debian:bullseye-slim

# 设置工作目录
WORKDIR /app

# 使用国内镜像源加速apt更新
RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    sed -i 's/security.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list

# 安装系统依赖和常用工具
RUN apt-get update && apt-get install -y \
    wget \
    tar \
    maven \
    git \
    lsof \
    vim \
    net-tools \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 复制JDK 21安装包到容器中并解压
COPY jdk21tar/openjdk-21.0.2_linux-x64_bin.tar.gz /tmp/jdk-21.tar.gz
RUN tar -xzf /tmp/jdk-21.tar.gz -C /opt/ && \
    rm -f /tmp/jdk-21.tar.gz

# 复制构建好的JAR文件到容器中
COPY target/super-jacoco.jar /app/super-jacoco.jar

# 复制jacoco目录到容器中
COPY jacoco/ /home/test/super-jacoco/jacoco/

# 复制SQL文件到容器中
COPY sql/ /home/test/super-jacoco/sql/

# 创建必要的目录结构
RUN mkdir -p /home/test/super-jacoco/app/super_jacoco/clonecode \
    /home/test/super-jacoco/report/logs \
    /home/test/super-jacoco/report \
    /home/test/super-jacoco/resource/jacoco-resources \
    /home/test/super-jacoco/cover \
    /home/test/super-jacoco/sql

# 设置Java环境变量
ENV JAVA_HOME=/opt/jdk-21.0.2
ENV PATH=$JAVA_HOME/bin:$PATH

# 暴露端口
EXPOSE 8899

# 设置启动命令
CMD ["java", "-jar", "super-jacoco.jar"]