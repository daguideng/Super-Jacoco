package com.xiaoju.basetech.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @description:
 * @author: charlynegaoweiwei
 * @time: 2020/4/26 7:52 PM
 */
@Data
@Schema(description = "环境覆盖率请求类")
public class EnvCoverRequest extends CoverBaseRequest{

    @NotNull(message = "address不能为空")
    @Schema(description = "被测项目地址", example = "189.288.807.61", required = true)
    private String address;
    
    @NotNull(message = "port不能为空")
    @Schema(description = "被测项目的 super-jacoco-runtime.jar 的端口", example = "18513", required = true)
    private int port;

}