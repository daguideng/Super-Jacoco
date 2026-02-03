#!/bin/bash

# 启动脚本 - Super JaCoCo 容器化部署

echo "=== Super JaCoCo 容器化部署脚本 ==="

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    echo "错误: Docker 未安装，请先安装Docker"
    exit 1
fi

# 检查Docker Compose是否安装
if ! command -v docker compose &> /dev/null; then
    echo "错误: Docker Compose 未安装，请先安装Docker Compose"
    exit 1
fi

# 默认使用生产环境配置
COMPOSE_FILE="docker-compose.yml"
ENV_NAME="生产环境"

# 检查是否指定了开发环境
if [ "$2" = "dev" ]; then
    COMPOSE_FILE="docker-compose.dev.yml"
    ENV_NAME="开发环境"
fi

echo "使用配置: $COMPOSE_FILE ($ENV_NAME)"

case "$1" in
    "start")
        echo "启动 Super JaCoCo 服务 ($ENV_NAME)..."
        docker-compose -f $COMPOSE_FILE up -d
        echo "服务启动完成!"
        echo "应用地址: http://localhost:8899"
        echo "Swagger文档: http://localhost:8899/doc.html"
        if [ "$COMPOSE_FILE" = "docker-compose.yml" ]; then
            echo "MySQL端口: 13306"
        else
            echo "开发环境: 使用外部MySQL (端口: 13306)"
        fi
        ;;
    "stop")
        echo "停止 Super JaCoCo 服务 ($ENV_NAME)..."
        docker-compose -f $COMPOSE_FILE down
        echo "服务已停止"
        ;;
    "restart")
        echo "重启 Super JaCoCo 服务 ($ENV_NAME)..."
        docker-compose -f $COMPOSE_FILE down
        docker-compose -f $COMPOSE_FILE up -d
        echo "服务重启完成!"
        ;;
    "build")
        echo "构建 Super JaCoCo 镜像 ($ENV_NAME)..."
        echo "第一步: 构建项目JAR文件..."
        mvn clean package -DskipTests
        echo "第二步: 构建Docker镜像..."
        docker-compose -f $COMPOSE_FILE build --no-cache
        echo "镜像构建完成!"
        ;;
    "logs")
        echo "查看服务日志 ($ENV_NAME)..."
        docker-compose -f $COMPOSE_FILE logs -f
        ;;
    "status")
        echo "服务状态 ($ENV_NAME):"
        docker-compose -f $COMPOSE_FILE ps
        ;;
    "clean")
        echo "清理容器和镜像 ($ENV_NAME)..."
        docker-compose -f $COMPOSE_FILE down -v
        docker system prune -f
        echo "清理完成!"
        ;;
    "dev")
        echo "切换到开发环境..."
        docker-compose -f docker-compose.dev.yml up -d
        echo "开发环境启动完成!"
        echo "应用地址: http://localhost:8899"
        echo "源代码已挂载，支持热重载"
        ;;
    "prod")
        echo "切换到生产环境..."
        docker-compose -f docker-compose.yml up -d
        echo "生产环境启动完成!"
        echo "应用地址: http://localhost:8899"
        echo "MySQL端口: 13306"
        ;;
    *)
        echo "使用方法: $0 {start|stop|restart|build|logs|status|clean} [dev]"
        echo "  start [dev]   - 启动服务 (可选: dev=开发环境)"
        echo "  stop [dev]    - 停止服务 (可选: dev=开发环境)"
        echo "  restart [dev] - 重启服务 (可选: dev=开发环境)"
        echo "  build [dev]   - 重新构建镜像 (可选: dev=开发环境)"
        echo "  logs [dev]    - 查看日志 (可选: dev=开发环境)"
        echo "  status [dev]  - 查看状态 (可选: dev=开发环境)"
        echo "  clean [dev]   - 清理容器和镜像 (可选: dev=开发环境)"
        echo "  dev           - 快速切换到开发环境"
        echo "  prod          - 快速切换到生产环境"
        echo ""
        echo "环境说明:"
        echo "  生产环境 (docker-compose.yml) - 包含完整的MySQL服务栈"
        echo "  开发环境 (docker-compose.dev.yml) - 简化配置，源代码挂载"
        ;;
esac