package com.example.commonsettings.configmanager;

public interface ConfigAccessor {
    boolean setString(String key, String value);
    boolean setInt(String key, int value);
    boolean setBoolean(String key, boolean value);
    boolean setLong(String key, long value);
    String getString(String key, String defaultValue);
    int getInt(String key, int defaultValue);
    boolean getBoolean(String key, boolean defaultValue);
    long getLong(String key, long defaultValue);
    float getFloat(String key, float defaultValue);
    boolean setFloat(String key, float value);

    boolean reset(String key);
}
