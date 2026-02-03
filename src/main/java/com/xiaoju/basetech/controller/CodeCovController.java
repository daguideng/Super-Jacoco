package com.xiaoju.basetech.controller;


import com.xiaoju.basetech.entity.*;
import com.xiaoju.basetech.service.CodeCovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author guojinqiong
 */
@RestController
@RequestMapping(value = "/cov")
public class CodeCovController {

    @Autowired
    private CodeCovService codeCovService;

    /**
     * 单测覆盖率接口-启动覆盖率收集
     *
     * @param unitCoverRequest 单元测试覆盖率请求参数
     * @return 操作结果
     */
    @io.swagger.v3.oas.annotations.Operation(summary = "启动覆盖率收集", description = "启动单元测试覆盖率数据收集")
    @io.swagger.v3.oas.annotations.tags.Tag(name = "单元测试覆盖率")
    @PostMapping(value = "/triggerUnitCover")
    public HttpResult<Boolean> triggerUnitCover(@RequestBody @Validated UnitCoverRequest unitCoverRequest) {
        codeCovService.triggerUnitCov(unitCoverRequest);
        return HttpResult.success();
    }


    /**
     * 单测覆盖率接口-获取覆盖率结果
     *
     * @param uuid，触发时携带的UUID
     * @return coverStatus：-1、失败;1、成功；0、进行中
     */
    @io.swagger.v3.oas.annotations.Operation(summary = "获取覆盖率结果", description = "获取单元测试覆盖率结果")
    @io.swagger.v3.oas.annotations.tags.Tag(name = "单元测试覆盖率")
    @GetMapping(value = "/getUnitCoverResult")
    @ResponseBody
    public HttpResult<CoverResult> getCoverResult(@RequestParam(value = "uuid") String uuid) {
        return HttpResult.success(codeCovService.getCoverResult(uuid));
    }

    /**
     * 环境覆盖率接口-启动覆盖率收集
     *
     * @param envCoverRequest 环境覆盖率请求参数
     * @return 操作结果
     */
    @io.swagger.v3.oas.annotations.Operation(summary = "启动覆盖率收集", description = "启动环境覆盖率数据收集")
    @io.swagger.v3.oas.annotations.tags.Tag(name = "环境覆盖率")
    @PostMapping(value = "/triggerEnvCov")
    public HttpResult<Boolean> triggerEnvCov(@RequestBody @Validated EnvCoverRequest envCoverRequest) {
        codeCovService.triggerEnvCov(envCoverRequest);
        return HttpResult.success();
    }

    /**
     * 环境覆盖率接口-获取覆盖率结果
     *
     * @param uuid 唯一标识符
     * @return 覆盖率结果
     */
    @io.swagger.v3.oas.annotations.Operation(summary = "获取覆盖率结果", description = "获取环境覆盖率测试结果")
    @io.swagger.v3.oas.annotations.tags.Tag(name = "环境覆盖率")
    @GetMapping(value = "/getEnvCoverResult")
    public HttpResult<CoverResult> getEnvCoverResult(@RequestParam(value = "uuid") String uuid) {
        return HttpResult.success(codeCovService.getCoverResult(uuid));
    }

    /**
     * 单测覆盖率接口-获取覆盖率结果
     *
     * @param request 请求参数
     * @return 覆盖率结果
     */

    /** 
    @io.swagger.v3.oas.annotations.Operation(summary = "获取覆盖率结果", description = "获取单元测试覆盖率结果", hidden = true)
    @io.swagger.v3.oas.annotations.tags.Tag(name = "单元测试覆盖率")
    @PostMapping(value = "/getLocalCoverResult")
    public HttpResult<CoverResult> getLocalCoverResult(@RequestBody @Validated LocalHostRequestParam request) {
        return HttpResult.success(codeCovService.getLocalCoverResult(request));
    }
    ***/
}
