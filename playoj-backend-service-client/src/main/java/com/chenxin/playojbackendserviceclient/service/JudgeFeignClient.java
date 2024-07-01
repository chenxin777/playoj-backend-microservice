package com.chenxin.playojbackendserviceclient.service;


import com.chenxin.playojbackendmodel.model.entity.UserQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fangchenxin
 * @description 判题服务
 * @date 2024/6/17 17:03
 * @modify
 */
@FeignClient(name = "playoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * @description 执行判题
     * @author fangchenxin
     * @date 2024/6/17 23:47
     * @param userQuestionId
     * @return com.chenxin.playojbackend.model.entity.UserQuestion
     */
    @PostMapping("/do")
    UserQuestion doJudge(@RequestParam("userQuestionId") long userQuestionId);



}
