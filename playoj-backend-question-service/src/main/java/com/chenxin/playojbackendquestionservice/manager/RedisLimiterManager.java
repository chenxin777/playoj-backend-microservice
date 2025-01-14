package com.chenxin.playojbackendquestionservice.manager;

import com.chenxin.playojbackendcommon.common.ErrorCode;
import com.chenxin.playojbackendcommon.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description 专门提供RedisLimiter限流基础服务 (提供通用能力)
 * @date 2024/6/5 18:09
 * @modify
 */
@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * @param key 区分不同的限流器，比如不同的用户id应该分别统计
     * @description
     * @author fangchenxin
     * @date 2024/6/5 18:26
     */
    public void doRateLimit(String key) {
        // 创建一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 1, 20, RateIntervalUnit.SECONDS);
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp) {
            throw new BusinessException(ErrorCode.MANY_REQUEST);
        }
    }
}
