package com.primax.dbconfig;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0) //这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
@Slf4j
public class DataSourceSwitchAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceSwitchAspect.class);

    //    @Pointcut("@within(DataSource) || @annotation(DataSource)")
//    private void dbAspect() {
//    }
//
//    @Before("dbAspect() && @annotation(dataSource) ")
//    public void db1(DataSource dataSource) {
//        log.info("切换数据源...");
//        DbContextHolder.setDbType(dataSource.value());
//    }
//
//    @After("dbAspect()")
//    public void doAfter(){
//        DbContextHolder.clearDbType();
//    }
    @Pointcut("execution(* com.primax.service.referral..*.*(..))")
    private void db1Aspect() {
    }

    @Pointcut("execution(* com.primax.service.WxEmployeeUserCQService.*(..))")
    private void db2Aspect() {
    }

    @Pointcut("execution(* com.primax.service.WxEmployeeUserKSService.*(..))")
    private void db3Aspect() {
    }

    @Before("db1Aspect() ")
    public void db1() {
        logger.info("切换到db1 员工绑定企业微信数据源...");
        DbContextHolder.setDbType(DBTypeEnum.db1);
    }

    @Before("db2Aspect()")
    public void db2() {
        logger.info("切换到db2 医疗挂号数据源...");
        DbContextHolder.setDbType(DBTypeEnum.db2);
    }

    @Before("db3Aspect()")
    public void db3() {
        logger.info("切换到db2 医疗挂号数据源...");
        DbContextHolder.setDbType(DBTypeEnum.db3);
    }

}
