package com.chenxin.playojbackendquestionservice.controller.inner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chenxin.playojbackendmodel.model.entity.Question;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import com.chenxin.playojbackendquestionservice.service.QuestionService;
import com.chenxin.playojbackendquestionservice.service.UserQuestionService;
import com.chenxin.playojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description 该服务仅内部调用，不提供给前端
 * @date 2024/6/30 14:38
 * @modify
 */
@RestController
@RequestMapping(("/inner"))
public class InnerQuestionController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserQuestionService userQuestionService;

    @Override
    @GetMapping("/get/id")
    public Question getById(long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public UserQuestion getQuestionSubmitById(long userQuestionId) {
        return userQuestionService.getById(userQuestionId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(UserQuestion userQuestion) {
        return userQuestionService.updateById(userQuestion);
    }

    @Override
    @PostMapping("/update/id")
    public boolean updateQuestionAcceptNum(@RequestBody long questionId) {
        UpdateWrapper<Question> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", questionId);
        updateWrapper.setSql("acceptNum = acceptNum + 1");
        return questionService.update(updateWrapper);
    }
}
