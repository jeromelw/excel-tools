package io.ishuk.excel.tools.internal.util;

import jdk.internal.joptsimple.internal.Strings;

import java.util.Objects;

public class Assert {

    public static void state(boolean expression, String message){
        if(!expression){
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression, String message, Object... additional){
        if(!expression){
            throw new IllegalStateException(String.format(message,additional));
        }
    }

    public static void isNull(Object object, String message){
        if(object != null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object, String message, Object... additional){
        if(object != null){
            throw new IllegalArgumentException(String.format(message,additional));
        }
    }

    public static void notNull(Object object, String message){
        if(null == object){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message, Object... additional){
        if(object == null){
            throw new IllegalArgumentException(String.format(message,additional));
        }
    }

    public static void hasLength(String text, String message){
        if(Strings.isNullOrEmpty(text)){
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasLength(String text, String message, Object... additional){
        if(Strings.isNullOrEmpty(text)){
            throw new IllegalArgumentException(String.format(message,additional));
        }
    }

    public static void equals(Object o1, Object o2, String message){
        if(!Objects.equals(o1,o2)){
            throw new IllegalArgumentException(message);
        }
    }

    public static void equals(Object o1, Object o2, String message,Object... additional){
        if(!Objects.equals(o1,o2)){
            throw new IllegalArgumentException(String.format(message,additional));
        }
    }
}
