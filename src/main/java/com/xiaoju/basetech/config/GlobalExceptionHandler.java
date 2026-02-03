package com.xiaoju.basetech.config;



import com.xiaoju.basetech.entity.ErrorCode;
import com.xiaoju.basetech.entity.HttpResult;
import com.xiaoju.basetech.entity.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author guojinqiong
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * response异常处理拦截器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    HttpResult handleException(Exception e) {
        log.error("{}", e);
        return HttpResult.build(ErrorCode.FAIL);
    }

    /**
     * response异常处理拦截器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ResponseException.class)
    HttpResult handleResponseException(ResponseException e) {
        log.error("{}", e);
        return HttpResult.build(e.getErrorCode(), e.getMsg());
    }

    /**
     * 数据校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    HttpResult handleValidateException(MethodArgumentNotValidException e) {
        log.error("{}", e);
        return HttpResult.build(ErrorCode.FAIL, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 静态资源未找到异常处理（如favicon.ico）
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    HttpResult handleNoResourceFoundException(NoResourceFoundException e) {
        // 对于静态资源未找到，记录debug级别日志即可，避免过多错误日志
        log.debug("静态资源未找到: {}", e.getMessage());
        
        // 对于覆盖率报告路径，不进行拦截，让Spring Boot返回默认的404页面
        if (e.getMessage() != null && e.getMessage().contains("/bada")) {
            log.debug("跳过覆盖率报告路径的异常处理: {}", e.getMessage());
            // 不处理此异常，让Spring Boot返回默认的404响应
            return null;
        }
        
        return HttpResult.build(ErrorCode.NOT_FOUND, "请求的资源不存在");
    }

}
