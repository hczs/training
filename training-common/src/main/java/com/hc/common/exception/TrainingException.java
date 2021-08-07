package com.hc.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ：hc
 * @date ：Created in 2021/2/21 20:10
 * @modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingException extends RuntimeException{
    private Integer code;
    private String message;
}
