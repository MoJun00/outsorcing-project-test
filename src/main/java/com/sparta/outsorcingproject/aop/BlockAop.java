package com.sparta.outsorcingproject.aop;

import com.sparta.outsorcingproject.entity.UserRoleEnum;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BlockAop {

    @Around("execution(* com.sparta.outsorcingproject.controller.ReviewController.addReview(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 현재 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            log.info("User: " + userDetails.getUsername());

            // 차단된 사용자인지 확인
            if (userDetails.getUser().getRole().equals(UserRoleEnum.BLOCK) ) {
                throw new IllegalArgumentException("차단된 유저는 이 작업을 수행할 수 없습니다.");
            }
        }
        return joinPoint.proceed();
    }
}