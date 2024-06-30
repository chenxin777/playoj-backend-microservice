package com.chenxin.playojbackendjudgeservice.judge;

import com.chenxin.playojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.chenxin.playojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.chenxin.playojbackendjudgeservice.judge.strategy.JudgeContext;
import com.chenxin.playojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.chenxin.playojbackendmodel.model.dto.userquestion.JudgeInfo;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import com.chenxin.playojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * @author fangchenxin
 * @description 判题管理
 * @date 2024/6/17 23:01
 * @modify
 */
@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        UserQuestion userQuestion = judgeContext.getUserQuestion();
        String language = userQuestion.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
