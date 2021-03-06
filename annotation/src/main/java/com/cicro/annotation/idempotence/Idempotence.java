package com.cicro.annotation.idempotence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //注解作用于方法上
@Retention(RetentionPolicy.RUNTIME) //保留到运行时
public @interface Idempotence {
}
