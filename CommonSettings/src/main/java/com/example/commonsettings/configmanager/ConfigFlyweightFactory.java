package com.example.commonsettings.configmanager;

import java.util.HashMap;
import java.util.Map;

public class ConfigFlyweightFactory {

    private Map<String, ConfigAccessor> providers = new HashMap<>();
    private Map<String, DefaultValueLoader> defaultLoaders = new HashMap<>();

    private Map<ConfigManager.Type, Map<String, Map<String, ConfigFlyweight>>> configInfos = new HashMap<>();

    public void registerAccessor(String name, ConfigAccessor configAccessor) {
        providers.put(name, configAccessor);
    }

    public void registerDefaultLoader(String name, DefaultValueLoader defaultValueLoader) {
        defaultLoaders.put(name, defaultValueLoader);
    }

    ConfigFlyweight getInfo(ConfigManager.Type type, String providerName, String defaultLoaderName) {
        ConfigAccessor provider = providers.get(providerName);
        DefaultValueLoader loader = defaultLoaders.get(defaultLoaderName);

        if (provider == null || loader == null) {
            throw new ConfigRuntimeException("the provier name : " + providerName + " or default loader name : "
                    + defaultLoaderName + " has not registered yet !!");
        }

        Map<String, Map<String, ConfigFlyweight>> providerAndLoaders = configInfos.get(type);

        if (providerAndLoaders == null) {
            providerAndLoaders = new HashMap<>();
            configInfos.put(type, providerAndLoaders);
        }

        Map<String, ConfigFlyweight> loaders = providerAndLoaders.get(providerName);

        if (loaders == null) {
            loaders = new HashMap<>();
            providerAndLoaders.put(providerName, loaders);
        }

        ConfigFlyweight configFlyweight = loaders.get(defaultLoaderName);

        if (configFlyweight == null) {
            configFlyweight = new ConfigFlyweight(type, providers.get(providerName), defaultLoaders.get(defaultLoaderName));
            loaders.put(defaultLoaderName, configFlyweight);
        }

        return configFlyweight;
    }
}
