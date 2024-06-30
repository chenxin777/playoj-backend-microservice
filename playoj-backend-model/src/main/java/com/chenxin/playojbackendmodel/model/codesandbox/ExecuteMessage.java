package com.chenxin.playojbackendmodel.model.codesandbox;

import lombok.Data;

/**
 * @author fangchenxin
 * @description
 * @date 2024/6/18 18:16
 * @modify
 */
@Data
public class ExecuteMessage {

    /**
     * 执行结果标志
     */
    private Integer exitValue;

    /**
     * 执行结果
     */
    private String message;

    /**
     * 执行错误信息
     */
    private String errorMessage;

    /**
     * 消耗时间
     */
    private Long time;

    /**
     * 占用内存
     */
    private Long memory;

}
