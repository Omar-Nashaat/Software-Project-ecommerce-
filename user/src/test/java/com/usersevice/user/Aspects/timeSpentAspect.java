// package com.usersevice.user.Aspects;

// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// @Aspect
// @Order(1)
// @Component
// public class timeSpentAspect {

//     Logger log = LoggerFactory.getLogger(timeSpentAspect.class);

//     @Around(value = "execution(* com.usersevice.user.Services..*(..))")
//     public Object timeSpent(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//         long startTime = System.currentTimeMillis();
//         StringBuilder sb = new StringBuilder("KPI :");
//         sb.append("[").append(proceedingJoinPoint.getKind()).append("]\tfor:")
//                 .append(proceedingJoinPoint.getSignature().getName());
//         sb.append("\ttook : ");
//         Object returnValue =proceedingJoinPoint.proceed();
//         log.info(sb.append(System.currentTimeMillis() -startTime).append("ms .").toString());
//         return returnValue;
//     }

// }