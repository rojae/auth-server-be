package io.github.rojae.authcoreapi.common.aspect;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

	@Value("${logging.on-off}")
	private boolean onOff;
	@Value("${logging.profile}")
	private String profile;
	@Value("${logging.warning-time-ms}")
	private int warningTimeMs;

	@Around("@annotation(io.github.rojae.authcoreapi.common.aspect.LogExecutionTime)")
	public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		try {
			return proceedingJoinPoint.proceed();
		}
		finally {
			// on-off 설정
			if(onOff){
				MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
				String className = methodSignature.getDeclaringType().getSimpleName();
				String methodName = methodSignature.getName();

				long end = System.currentTimeMillis();
				long time = end - start;

				String params = Arrays.stream(proceedingJoinPoint.getArgs()).map(s -> "\""+s.toString()+"\"").collect(Collectors.joining(","));

				// dev 환경은 항상 로깅, log.info
				if(this.profile.equalsIgnoreCase("dev")){
					log.info(String.format("%s.%s(%s)", className, methodName, params) + " execution in " + time + "ms");
				}

				// 설정해둔 시간보다 긴 경우, log.warn
				if (time > warningTimeMs){
						log.warn(String.format("%s.%s(%s)", className, methodName, params) + " execution longer than " + warningTimeMs + "ms! ("+time+"ms)");
				}
			}
		}
	}
}