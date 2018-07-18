package io.ishuk.excel.tools.entity;

import java.util.function.Function;

public class DefaultFunction implements Function {
    @Override
    public Object apply(Object o) {
        return o;
    }
}
