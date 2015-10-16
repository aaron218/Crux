package net.newString.crux.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by aaron on 7/1/2015.
 * 用于表示工具方法状态的  注解仅在源文件有效
 * value 部分是作者名称，可以不写
 * version 部分是版本号，可以不写
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface stable {
    String value() default "default";

    String version() default "0.1.0-SNAPSHOT";
}
