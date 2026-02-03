package com.xiaoju.basetech.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @description:
 * @author: charlynegaoweiwei
 * @time: 2020/4/26 7:52 PM
 */
@Data
@Schema(description = "覆盖率请求基础类")
public class CoverBaseRequest {

    /**
     * uuid是必须的，在后续查询结果时需要使用
     */
    @NotBlank(message = "uuid不能为空")
    @Schema(description = "准备一个不会重复的，没有使用过的 UUID", example = "uuid", required = true)
    private String uuid;

    @NotBlank(message = "gitUrl不能为空")
    @Schema(description = "被测项目地址", example = "https://gitee.com/mua-mua/dorm_back.git", required = true)
    private String gitUrl;

    //@NotBlank(message = "baseVersion不能为空")
    @Schema(description = "被测项目上一次代码的commitID、分支名称", example = "master", defaultValue = "master")
    private String baseVersion="master";

    @NotBlank(message = "nowVersion不能为空")
    @Schema(description = "被测项目目前代码的commitID、分支名称", example = "master", required = true)
    private String nowVersion;

    /**
     * 同一个git仓库可能存在多个模块，subModule为相对路径，如果为空，则代表整个git仓库
     */
    @Schema(description = "子模块路径", example = "")
    private String subModule;

    /**
     * 1、全量；2、增量
     */
    @NotNull(message = "type不能为空")
    @Max(value = 2)
    @Min(value = 1)
    @Schema(description = "2=增量覆盖，1=全量覆盖", example = "2", required = true)
    private Integer type;
}