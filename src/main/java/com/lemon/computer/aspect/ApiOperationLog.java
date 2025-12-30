package com.lemon.computer.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {

    /**
     * API功能描述
     * @return  功能描述
     */
    String description() default "";

}
