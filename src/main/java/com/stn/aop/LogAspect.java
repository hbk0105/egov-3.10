package com.stn.aop;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;


@Aspect
@Component
public class LogAspect {

    // private Logger log = LoggerFactory.getLogger(this.getClass());
    private org.apache.logging.log4j.Logger log = LogManager.getLogger(this.getClass());
    // https://kingjakeu.github.io/springboot/2021/02/02/spring-aop/
    /* // 패키지 범위 설정 */
    @Pointcut("@annotation(com.stn.aop.RequestingPayment)")
    public void cut() {}

    // Joint Point가 실행되기 전(결제 추가 인증)
    @Before("cut()")
    public void onBefore(JoinPoint joinPoint) {

        log.info("--------- onBefore START ---------");

        HashMap<String, Object> param = getParam();
        log.info("onBefore[param] :: -> " + param);

        log.info("--------- onBefore END ---------");

    }

    // Joint Point가 실행된 후(결제 추가 인증)
    @AfterReturning(pointcut = "cut()", returning = "returnObj")
    public void onAfterReturning(JoinPoint joinPoint, Object returnObj){
        // 메서드 정보 받아오기
        Method method = getMethod(joinPoint);
        log.info("--------- onAfterReturning START ---------");
        log.info("======= method name = {} =======", method.getName());
        log.info("AfterAllMethod = {}", joinPoint.getSignature());
        log.info("return value = {}", returnObj);

        HashMap<String, Object> param = getParam();
        log.info("onAfterReturning[param] :: -> " + param);

        log.info("--------- onAfterReturning END ---------");

    }

    // JoinPoint로 메서드 정보 가져오기
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }


    // JointPoint 실행 전, 후
    @Around("cut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("--------- around START ---------");

        //메서드 시작 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed(); // JointPoint 실행

        String type = joinPoint.getSignature().getDeclaringTypeName();
        String name = "";
        if (type.indexOf("Controller") > -1) {
            name = "Controller : ";
        }
        else if (type.indexOf("Service") > -1) {
            name = "ServiceImpl : ";
        }
        else if (type.indexOf("DAO") > -1) {
            name = "DAO : ";
        }
        log.info(name + type + "." + joinPoint.getSignature().getName() + "()");
        //메서드 종료 후
        stopWatch.stop();
        log.info(" Total Running Time : " + stopWatch.getTotalTimeSeconds());


        log.info("--------- around END ---------");
    }

    /* 오류 발생 시 */
    @AfterThrowing(pointcut = "cut()", throwing = "e")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception e) {
        log.info("--------- onAfterTrowing START ---------");

        log.error("### Occured error in request {}", joinPoint.getSignature().toShortString());
        log.error("\t{}", e.getMessage());

        log.info("--------- onAfterTrowing END ---------");

    }


    private HashMap<String, Object> getParam(){
        HashMap<String, Object> param = new HashMap<>();

        // 전달 된 파라미터로 pay , order 테이블 결제 테이블 수정 필요
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =requestAttributes.getRequest();

        Enumeration p = request.getParameterNames();
        while (p.hasMoreElements()){
            String name = (String)p.nextElement();
            param.put(name,request.getParameter(name));
        }
        return param;
    }

    /* <aop:aspectj-autoproxy /> 설정 필수 */


}
