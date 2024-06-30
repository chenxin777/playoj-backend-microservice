package com.chenxin.playojbackendjudgeservice.judge.strategy;

import com.chenxin.playojbackendmodel.model.dto.question.JudgeCase;
import com.chenxin.playojbackendmodel.model.dto.userquestion.JudgeInfo;
import com.chenxin.playojbackendmodel.model.entity.Question;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import lombok.Data;

import java.util.List;

/**
 * @author fangchenxin
 * @description 上下文（由于定义在策略中传递的参数）
 * @date 2024/6/17 19:21
 * @modify
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private UserQuestion userQuestion;
}
