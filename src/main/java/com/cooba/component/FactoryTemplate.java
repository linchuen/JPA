package com.cooba.component;

import com.cooba.interfaces.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FactoryTemplate<E, T extends Component<E>> {
    private final Map<E, T> templateMap;

    public FactoryTemplate(List<T> tList) {
        templateMap = tList.stream().collect(Collectors.toMap(Component::getEnum, Function.identity()));
    }

    public abstract T getByType(int type);

    public T getByEnum(E templateEnum) {
        return templateMap.get(templateEnum);
    }
}
