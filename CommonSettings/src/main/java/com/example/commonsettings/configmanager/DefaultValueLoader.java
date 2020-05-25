package com.example.commonsettings.configmanager;

public interface DefaultValueLoader {
    String getString(String key);
    int getInt(String key);
    boolean getBoolean(String key);
    long getLong(String key);
    float getFloat(String key);
}
