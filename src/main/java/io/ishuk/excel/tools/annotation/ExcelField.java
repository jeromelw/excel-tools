package io.ishuk.excel.tools.annotation;

import io.ishuk.excel.tools.entity.DefaultFunction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {

    String columnName() default "";

    Class<? extends Function> writeConvert() default DefaultFunction.class;

    Class<? extends Function> readConvert() default DefaultFunction.class;
}
