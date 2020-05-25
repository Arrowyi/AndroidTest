package com.example.commonsettings.configmanager;


class ConfigFlyweight {

    private ConfigManager.Type type;
    private ConfigAccessor configAccessor;
    private DefaultValueLoader defaultValueLoader;

    ConfigFlyweight(ConfigManager.Type type, ConfigAccessor configAccessor, DefaultValueLoader defaultValueLoader) {
        this.type = type;
        this.configAccessor = configAccessor;
        this.defaultValueLoader = defaultValueLoader;
    }

    ConfigManager.Type getType() {
        return type;
    }

    ConfigAccessor getConfigAccessor() {
        return configAccessor;
    }

    DefaultValueLoader getDefaultValueLoader() {
        return defaultValueLoader;
    }


}
