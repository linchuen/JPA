package com.cooba.util.json;

import java.util.List;

public interface Json {
    <T> String toJsonString(T value);

    <T> T parseObject(String json, Class<T> clazz);

     <T> List<T> parseList(String json, Class<T> clazz);
}
