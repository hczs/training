package com.hc.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回封装
 * @author hczs8
 */
@Data
public class TrainingResult {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<>();

    /**
     * 构造方法私有化，单例模式
     */
    private TrainingResult(){};

    /**
     * 给外界提供获取对象的静态方法
     * @return TrainingResult
     */
    public static TrainingResult ok(){
        TrainingResult trainingResult = new TrainingResult();
        trainingResult.setSuccess(true);
        trainingResult.setCode(ResultStatus.SUCCESS.getCode());
        trainingResult.setMessage("成功");
        return trainingResult;
    }

    public static TrainingResult error(){
        TrainingResult trainingResult = new TrainingResult();
        trainingResult.setSuccess(false);
        trainingResult.setCode(ResultStatus.ERROR.getCode());
        trainingResult.setMessage("失败");
        return trainingResult;
    }

    /**
     * 以下设置是为了方便后面链式编程
     */
    public TrainingResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public TrainingResult message(String message){
        this.setMessage(message);
        return this;
    }

    public TrainingResult status(Integer status){
        this.setCode(status);
        return this;
    }

    public TrainingResult data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public TrainingResult data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
