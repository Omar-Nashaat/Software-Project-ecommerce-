// package com.usersevice.user.Aspects;

// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// @Aspect
// @Order(0)
// @Component
// public class nameArgsAspect {

//     private final Logger log = LoggerFactory.getLogger(nameArgsAspect.class);

//     @Pointcut(value = "execution(*  com.usersevice.user.Services..*(..))")
//     public void forServices(){}

//     @Pointcut(value = "execution(* com.usersevice.user.Controller..*(..))")
//     public void forController(){}

//     @Pointcut(value = "forServices() || forController()")
//     public void forAll(){}

//     @Before(value="forAll()")
//     public void beforeMethod(JoinPoint JoinPoint){
//         String methodName = JoinPoint.getSignature().getName();
//         log.info("====> Method name is -->>> {}",methodName);

//         Object [] args=JoinPoint.getArgs();
//         for(Object arg : args){
//             log.info(" ====> with arguments -->>> {}",arg);
//         }

//     }

// }