// package com.dave.fantasyfootball.aspect;
//
// import java.util.logging.Logger;
//
// import javassist.bytecode.SignatureAttribute.MethodSignature;
//
// @Aspect
// public class LoggingAspect {
// @Around("execution(* *(..)) && @annotation(Loggable)")
// public Object around(ProceedingJoinPoint point) {
// long start = System.currentTimeMillis();
// Object result = point.proceed();
// Logger.info(
// "#%s(%s): %s in %[msec]s",
// MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
// point.getArgs(),
// result,
// System.currentTimeMillis() - start
// );
// return result;
// }
// }