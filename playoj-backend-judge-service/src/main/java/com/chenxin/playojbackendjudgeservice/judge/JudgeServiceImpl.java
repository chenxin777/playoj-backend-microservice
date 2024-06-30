package com.chenxin.playojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.chenxin.playojbackendcommon.common.ErrorCode;
import com.chenxin.playojbackendcommon.exception.BusinessException;
import com.chenxin.playojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.chenxin.playojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.chenxin.playojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.chenxin.playojbackendjudgeservice.judge.strategy.JudgeContext;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.chenxin.playojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.chenxin.playojbackendmodel.model.dto.question.JudgeCase;
import com.chenxin.playojbackendmodel.model.dto.userquestion.JudgeInfo;
import com.chenxin.playojbackendmodel.model.entity.Question;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import com.chenxin.playojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.chenxin.playojbackendserviceclient.service.QuestionService;
import com.chenxin.playojbackendserviceclient.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangchenxin
 * @description
 * @date 2024/6/17 17:16
 * @modify
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserQuestionService userQuestionService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    /**
     * @param userQuestionId
     * @return com.chenxin.playojbackend.model.entity.UserQuestion
     * @description 执行判题
     * @author fangchenxin
     * @date 2024/6/17 23:48
     */
    @Override
    public UserQuestion doJudge(long userQuestionId) {
        // 查提交信息
        UserQuestion userQuestion = userQuestionService.getById(userQuestionId);
        if (userQuestion == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 判断状态,防止重复判题
        if (!QuestionSubmitStatusEnum.WAITING.getValue().equals(userQuestion.getStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在判题中,请勿重复提交");
        }
        // 查题目
        Long questionId = userQuestion.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 更改题目提交状态 判题中
        UserQuestion userQuestionUpdate = new UserQuestion();
        userQuestionUpdate.setId(userQuestionId);
        userQuestionUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean statusUpdateRes = userQuestionService.updateById(userQuestionUpdate);
        if (!statusUpdateRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }

        // 调用代码沙箱
        String language = userQuestion.getLanguage();
        String code = userQuestion.getCode();
        // 获取用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 调用判题机
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 沙箱工厂根据编程语言获取对应沙箱
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        // 沙箱代理类提供日志和其他功能
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);

        // 调用判题策略
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setUserQuestion(userQuestion);
        // 调用判题策略
        JudgeInfo judgeInfoRes = judgeManager.doJudge(judgeContext);
        // 更新判题状态
        userQuestionUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        userQuestionUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoRes));
        statusUpdateRes = userQuestionService.updateById(userQuestionUpdate);
        if (!statusUpdateRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }
        // 查用户提交信息，返回
        return userQuestionService.getById(userQuestionId);
    }
}
