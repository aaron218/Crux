package net.newstring.crux.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 不可变对象
 * Created by Aaron on 2016/12/7.
 */
@stable
@Target({ElementType.PACKAGE,ElementType.TYPE})
public @interface Immutable {
}
