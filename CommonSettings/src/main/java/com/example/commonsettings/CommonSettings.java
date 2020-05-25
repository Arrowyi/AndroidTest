package com.example.commonsettings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface CommonSettings {
    enum Type
    {
        BOOLEAN,
        INT,
        FLOAT,
        DOUBLE,
        STRING
    }

    Type type();
    String defaultValue() default "";
    String accessor() default "";
}
