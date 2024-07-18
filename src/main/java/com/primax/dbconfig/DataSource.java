package com.primax.dbconfig;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//弃用
public @interface DataSource {

    DBTypeEnum value() default DBTypeEnum.db1;

}
