package com.chenxin.playojbackendjudgeservice.controller.inner;

import com.chenxin.playojbackendjudgeservice.judge.JudgeService;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import com.chenxin.playojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description 该服务仅内部调用，不提供给前端
 * @date 2024/6/30 14:38
 * @modify
 */
@RestController
@RequestMapping(("/inner"))
public class InnerJudgeController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * @param userQuestionId
     * @return com.chenxin.playojbackend.model.entity.UserQuestion
     * @description 执行判题
     * @author fangchenxin
     * @date 2024/6/17 23:47
     */
    @Override
    @PostMapping("/do")
    public UserQuestion doJudge(@RequestParam("userQuestionId") long userQuestionId) {
        return judgeService.doJudge(userQuestionId);
    }

}
