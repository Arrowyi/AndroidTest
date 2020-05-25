package com.example.commonsettings.configmanager;


public class ConfigDef implements ConfigRegister {

    private ConfigFlyweightFactory configFlyweightFactory;
    private ConfigSteward steward;

    @Override
    public void registerKey(ConfigSteward steward, ConfigFlyweightFactory flyweightFactory) {
        this.steward = steward;
        this.configFlyweightFactory = flyweightFactory;

    }

    protected void register(String key, ConfigManager.Type type, String accessor, String defaultLoader) {
        steward.register(key, type, accessor, defaultLoader, false);
    }

    protected void registerAccessor(String name, ConfigAccessor accessor) {
        configFlyweightFactory.registerAccessor(name, accessor);
    }

    protected void registerDefaultValueLoader(String name, DefaultValueLoader loader) {
        configFlyweightFactory.registerDefaultLoader(name, loader);
    }


}
