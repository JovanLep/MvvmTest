package com.zhen.base.utils;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * mmkv存储
 *
 * @author Lespayne
 */
public class PreferUtils {

    private static MMKV mmkv;

    /**
     * Application里面初始化
     */
    public static void initPrefUtils(Context context) {
        MMKV.initialize(context);
    }

    public static void createFile(String fileName) {
        if (mmkv == null) {
            mmkv = MMKV.mmkvWithID(fileName, MMKV.SINGLE_PROCESS_MODE);
        }
    }

    public static void put(String key, Object object) {
        if (mmkv == null) {
            return;
        }
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else {
            mmkv.encode(key, (String) object);
        }
    }

    public static Object get(String key, Object defaultObject) {
        if (mmkv == null) {
            createFile(key);
        }
        if (defaultObject instanceof String) {
            return mmkv.decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mmkv.decodeInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mmkv.decodeBool(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mmkv.decodeFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mmkv.decodeLong(key, (Long) defaultObject);
        }
        return null;
    }

    public static void remove(String key) {
        mmkv.remove(key);
    }
}
