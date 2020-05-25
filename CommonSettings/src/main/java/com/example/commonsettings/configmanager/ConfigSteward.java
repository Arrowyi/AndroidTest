package com.example.commonsettings.configmanager;

import java.util.HashMap;
import java.util.Map;

public class ConfigSteward {

    private Map<String, ConfigFlyweight> keys = new HashMap<>();
    private ConfigFlyweightFactory configFlyweightFactory = new ConfigFlyweightFactory();


    public void register(String key, ConfigManager.Type type, String accessor, String defaultLoader, boolean overwrite) {
        ConfigFlyweight configFlyweight = configFlyweightFactory.getInfo(type, accessor, defaultLoader);
        register(key, configFlyweight, overwrite);
    }

    void register(ConfigRegister register) {
        register.registerKey(this, configFlyweightFactory);
    }


    boolean isKeyDefined(String key) {
        return keys.containsKey(key);
    }

    ConfigFlyweight getFlyweight(String key) {
        return keys.get(key);
    }

    private void register(String key, ConfigFlyweight configFlyweight, boolean overwrite) {
        if (!overwrite && isKeyDefined(key)) {
            throw new ConfigRuntimeException("the key : " + key + " has already defined !!");
        }

        keys.put(key, configFlyweight);
    }
}
