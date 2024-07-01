package com.chenxin.playojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.chenxin.playojbackendcommon.common.ErrorCode;
import com.chenxin.playojbackendcommon.exception.BusinessException;
import com.chenxin.playojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author fangchenxin
 * @description 远程代码沙箱
 * @date 2024/6/17 12:28
 * @modify
 */
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {

    public static final String AUTH_REQUEST_HEADER = "auth";

    public static final String AUTH_REQUEST_SECRET = "chenxin";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("正在调用远程代码沙箱");
        String url = "http://localhost:8090/sandbox/execute";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message：" + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
