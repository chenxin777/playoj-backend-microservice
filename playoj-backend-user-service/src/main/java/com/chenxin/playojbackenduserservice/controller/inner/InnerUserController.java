package com.chenxin.playojbackenduserservice.controller.inner;

import com.chenxin.playojbackendmodel.model.entity.User;
import com.chenxin.playojbackendserviceclient.service.UserFeignClient;
import com.chenxin.playojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author fangchenxin
 * @description 该服务仅内部调用，不提供给前端
 * @date 2024/6/30 14:38
 * @modify
 */
@RestController
@RequestMapping("/inner")
public class InnerUserController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * 根据id列表获取用户列表
     *
     * @param userIdSet
     * @return
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> userIdSet) {
        return userService.listByIds(userIdSet);
    }

    /**
     * 根据id获取用户
     *
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") Long userId) {
        return userService.getById(userId);
    }
}
