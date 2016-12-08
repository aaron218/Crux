package net.newString.crux.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Aaron on 2016/12/7.
 */
@stable
@Target({ElementType.PACKAGE,ElementType.TYPE})
public @interface Immutable {
}
