package com.chenxin.playojbackendjudgeservice.judge.codesandbox.impl;


import com.chenxin.playojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.chenxin.playojbackendmodel.model.dto.userquestion.JudgeInfo;
import com.chenxin.playojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.chenxin.playojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author fangchenxin
 * @description 示例代码沙箱（仅用于调试）
 * @date 2024/6/17 12:28
 * @modify
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
