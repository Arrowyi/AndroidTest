package com.example.commonsettings.configmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {

    private Map<String, List<ConfigChangedListener>> keyListeners = new ConcurrentHashMap<>();

    private static ConfigManager sInstance;

    public enum Type {
        INT(0),
        BOOLEAN(1),
        STRING(2),
        LONG(3),
        FLOAT(4);

        private int value;

        Type(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    public interface ConfigChangedListener {
        void onConfigChanged(String item);
    }

    private ConfigSteward steward;

    private ConfigManager(ConfigRegister configRegister) {
        this.steward = new ConfigSteward();
        steward.register(configRegister);
    }

    public static void initInstance(ConfigRegister configRegister) {
        sInstance = new ConfigManager(configRegister);
    }

    public static void init()
    {
        try {
            Class settings = Class.forName("com.example.commonsettings.configmanager.CommonSettingsDef");
            initInstance((ConfigRegister) settings.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static ConfigManager getInstance() {
        if (sInstance == null) {
            throw new ConfigRuntimeException("instance has not inited yet !!!");
        }

        return sInstance;
    }

    public void register(ConfigRegister configRegister) {
        steward.register(configRegister);
    }

    public void addChangedListener(String key, ConfigChangedListener listener) {

        List<ConfigChangedListener> listeners = keyListeners.get(key);

        if (listeners == null) {
            if (!steward.isKeyDefined(key)) {
                throw new ConfigRuntimeException("key : " + key + " has not been defined in settings yet !!!");
            }

            listeners = new ArrayList<>();
            keyListeners.put(key, listeners);
        }

        listeners.add(listener);
    }

    public void removeChangedListener(String key, ConfigChangedListener listener) {
        List<ConfigChangedListener> listeners = keyListeners.get(key);

        if (listeners == null) {
            ConfigLog.e("removeChangedListener didn't have key listener");
            return;
        }

        listeners.remove(listener);
    }


    public boolean setInt(final String key, final int value) {
        return setValue(key, new SettingsPredicate() {
            @Override
            public boolean doAction(String item) {
                if (checkKey(item, Type.INT)) {
                    return getFlyweight(key).getConfigAccessor().setInt(item, value);
                }

                return false;
            }
        });
    }


    public boolean setBoolean(final String key, final boolean value) {
        return setValue(key, new SettingsPredicate() {
            @Override
            public boolean doAction(String item) {
                if (checkKey(item, Type.BOOLEAN)) {
                    return getFlyweight(key).getConfigAccessor().setBoolean(item, value);
                }

                return false;
            }
        });
    }


    public boolean setString(final String key, final String value) {
        return setValue(key, new SettingsPredicate() {
            @Override
            public boolean doAction(String item) {
                if (checkKey(item, Type.STRING)) {
                    return getFlyweight(key).getConfigAccessor().setString(item, value);
                }

                return false;
            }
        });
    }

    public boolean setLong(final String key, final long value) {
        return setValue(key, new SettingsPredicate() {
            @Override
            public boolean doAction(String item) {
                if (checkKey(item, Type.LONG)) {
                    return getFlyweight(key).getConfigAccessor().setLong(item, value);
                }

                return false;
            }
        });
    }


    public int getInt(String key) {
        return (Integer) getValue(key, Type.INT);
    }


    public boolean getBoolean(String key) {
        return (Boolean) getValue(key, Type.BOOLEAN);
    }


    public String getString(String key) {
        return (String) getValue(key, Type.STRING);
    }

    public long getLong(String key) {
        return (Long) getValue(key, Type.LONG);
    }

    public float getFloat(String key) {
        return (Float) getValue(key, Type.FLOAT);
    }

    public boolean setFloat(final String key, final float value) {
        return setValue(key, new SettingsPredicate() {
            @Override
            public boolean doAction(String item) {
                if (checkKey(item, Type.FLOAT)) {
                    return getFlyweight(key).getConfigAccessor().setFloat(item, value);
                }
                return false;
            }
        });
    }

    public boolean checkKey(String key,  Type type) {
        if (!steward.isKeyDefined(key)) {
            ConfigLog.e("key : " + key + " is not defined");
            return false;
        }

        if (getFlyweight(key).getType() != type) {
            ConfigLog.e("key type is not right !!! key is " + key + " and check type is " + type);
            return false;
        }

        return true;
    }

    public boolean isKeyDefined(String key) {
        return steward.isKeyDefined(key);
    }

    public boolean reset(String key) {
        if (isKeyDefined(key)) {
            ConfigFlyweight configFlyweight = getFlyweight(key);
            if (configFlyweight != null) {
                configFlyweight.getConfigAccessor().reset(key);
                onSettingItemChanged(key);
                return true;
            }
        }

        return false;
    }

    private Object getValue(String key,  Type type) {
        if (!checkKey(key, type)) {
            throw new ConfigRuntimeException("key : " + "key and type : " + type + " is not right!!");
        }
        ConfigFlyweight configFlyweight = getFlyweight(key);
        if (configFlyweight == null) {
            throw new ConfigRuntimeException("config " + key + "is not registered correctly!!!");
        }

        DefaultValueLoader loader = configFlyweight.getDefaultValueLoader();
        ConfigAccessor accessor = configFlyweight.getConfigAccessor();
        if (loader == null || accessor == null) {
            throw new ConfigRuntimeException("config" + key + "'s default loader or accessor is not defined");
        }

        switch (type) {
            case STRING:
                return accessor.getString(key, loader.getString(key));
            case BOOLEAN:
                return accessor.getBoolean(key, loader.getBoolean(key));
            case INT:
                return accessor.getInt(key, loader.getInt(key));
            case LONG:
                return accessor.getLong(key, loader.getLong(key));
            case FLOAT:
                return accessor.getFloat(key, loader.getFloat(key));
            default:
                throw new RuntimeException("getValue type is not defined!!");
        }
    }

    private ConfigFlyweight getFlyweight(String key) {
        return steward.getFlyweight(key);
    }

    private void onSettingItemChanged(String item) {
        List<ConfigChangedListener> listeners = keyListeners.get(item);
        if (listeners != null) {
            for (ConfigChangedListener listener : listeners) {
                listener.onConfigChanged(item);
            }
        }
    }

    private interface SettingsPredicate {
        boolean doAction(String key);

    }

    private boolean setValue(String key, SettingsPredicate predicate) {
        if (predicate.doAction(key)) {
            onSettingItemChanged(key);
            return true;
        }

        return false;
    }
}
