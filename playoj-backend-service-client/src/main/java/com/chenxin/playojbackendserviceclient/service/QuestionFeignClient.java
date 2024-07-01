package com.chenxin.playojbackendserviceclient.service;


import com.chenxin.playojbackendcommon.common.ErrorCode;
import com.chenxin.playojbackendcommon.exception.BusinessException;
import com.chenxin.playojbackendcommon.exception.ThrowUtils;
import com.chenxin.playojbackendmodel.model.entity.Question;
import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fangchenxin
 * @description 针对表【question(题目表)】的数据库操作Service
 * @createDate 2024-06-13 18:33:45
 */
@FeignClient(name = "playoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    Question getById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    UserQuestion getQuestionSubmitById(@RequestParam("userQuestionId") long userQuestionId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody UserQuestion userQuestion);

    @PostMapping("/update/id")
    boolean updateQuestionAcceptNum(@RequestBody long questionId);

}
