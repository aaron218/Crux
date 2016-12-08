package net.newString.crux.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * "不建议使用" 注解
 * <p> 用于表示所注解的目标不建议使用，与{@link Deprecated}不同,
 * 本注解用于表示从性能和工具(或算法、数据结构)的特性来考虑,
 * 不建议使用但为了兼容性和实现接口方法而不得不存在的方法
 * <p>
 * <p>本注解可以添加描述简短的文字字符串，用于描述不建议使用的原因
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Disrecommend {
    String value() default "disrecommend";
}
