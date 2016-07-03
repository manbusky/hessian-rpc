package io.hessian.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * Created by manbu on 7/2/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HServiceMapping {

    /**
     *
     * name of hessian service
     *
     */
    String value() default "";

}
