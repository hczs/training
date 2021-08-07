package com.hc.common.handler;

import com.hc.common.exception.TrainingException;
import com.hc.common.utils.TrainingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理服务器所有异常
     * @param e Exception
     * @return TrainingResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public TrainingResult error(Exception e){
        e.printStackTrace();
        return TrainingResult.error().message("请求失败！服务器出现异常！");
    }

    /**
     * 处理特定（TrainingException）异常
     * @param e TrainingException
     * @return TrainingResult
     */
    @ExceptionHandler(TrainingException.class)
    @ResponseBody
    public TrainingResult error1(TrainingException e){
        e.printStackTrace();
        return TrainingResult.error().message(e.getMessage()).status(e.getCode());
    }

}
