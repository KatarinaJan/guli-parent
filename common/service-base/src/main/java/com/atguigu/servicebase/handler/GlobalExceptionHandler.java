package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.util.ExceptionUtil;
import com.atguigu.servicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-02 13:37
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 同一异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message("执行了全局异常");
    }

    /**
     * 特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了特定异常");
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}
