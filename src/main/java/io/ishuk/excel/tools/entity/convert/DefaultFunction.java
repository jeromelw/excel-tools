package io.ishuk.excel.tools.entity.convert;

import java.util.function.Function;

public class DefaultFunction implements Function {
    @Override
    public Object apply(Object o) {
        return o;
    }
}
