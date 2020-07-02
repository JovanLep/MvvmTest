package com.zhen.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 屏幕参数获取，转换工具类
 *
 * @author lixuan
 */
public class ScreenUtil {

    /**
     * dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f * (dipValue >= 0 ? 1 : -1));
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 调用该方法，可以获取刘海屏的px值，没刘海屏则返回0
     *
     * @param context 上下文环境
     * @return 刘海屏的px值
     */
    public static float notchsupport(Context context) {
        float notchLengthFloat = 0.0f;
        //判断手机厂商，目前8.0只有华为、荣耀、小米、oppo、vivo适配了刘海屏
        String phoneManufacturer = android.os.Build.BRAND.toLowerCase();
        switch (phoneManufacturer) {
            case "huawei":
            case "honor":
                //huawei,长度为length,单位px
                boolean haveInScreenEMUI = hasNotchInScreenEMUI(context);
                if (haveInScreenEMUI) {
                    int[] screenSize;
                    screenSize = getNotchSizeEMUI(context);
                    notchLengthFloat = screenSize[1];
                    //下面注释的是单独测试时的弹出消息
                    //Toast.makeText(context, "haveInScreen:" + haveInScreenEMUI + ",screenSize:" + length, Toast.LENGTH_LONG).show();
                }
                break;
            case "xiaomi":
                //xiaomi,单位px
                boolean haveInScreenMIUI = getNotchMIUI("ro.miui.notch", 0) == 1;
                if (haveInScreenMIUI) {
                    int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
                    int result = 0;
                    if (resourceId > 0) {
                        result = context.getResources().getDimensionPixelSize(resourceId);
                    }
                    notchLengthFloat = result;
                    //下面注释的是单独测试时的弹出消息
                    //Toast.makeText(context, "haveInScreen:" + haveInScreenMIUI + ",screenSize" + result, Toast.LENGTH_LONG).show();
                }
                break;
            case "vivo":
                //vivo,单位dp，高度27dp
                boolean haveInScreenVIVO = hasNotchAtVivo(context);
                if (haveInScreenVIVO) {
                    //下面注释的是单独测试时的弹出消息
                    //Toast.makeText(context, "haveInScreenVIVO:" + haveInScreenVIVO, Toast.LENGTH_LONG).show();
                    notchLengthFloat = dip2px(context, 27);
                }
                break;
            case "oppo":
                //oppo
                boolean haveInScreenOPPO = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
                if (haveInScreenOPPO) {
                    //下面注释的是单独测试时的弹出消息
                    //Toast.makeText(context, "haveInScreenOPPO:" + haveInScreenOPPO, Toast.LENGTH_LONG).show();
                    notchLengthFloat = 80;
                }
                break;
            default:
                break;
        }
        return notchLengthFloat;
    }

    /**
     * huawei
     *
     * @param context
     * @return
     */
    private static boolean hasNotchInScreenEMUI(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class loadClass = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = loadClass.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(loadClass);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    private static int[] getNotchSizeEMUI(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }

    private static int getNotchMIUI(final String key, final int def) {
        Method getIntMethod = null;
        try {
            getIntMethod = Class.forName("android.os.SystemProperties")
                    .getMethod("getInt", String.class, int.class);
            return (Integer) getIntMethod.invoke(null, key, def);
        } catch (Exception e) {
            Log.e("MainActivity", "Platform error: " + e.toString());
            return def;
        }
    }

    private static boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, 0x00000020);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtVivo Exception");
        }
        return ret;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return int 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return Math.max(result, (int) notchsupport(context));
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文环境
     * @return int[屏幕宽度，屏幕高度]
     */
    public static int[] getScreenSize(Context context) {
        WindowManager windowManager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return new int[]{width, height};
    }

    /**
     * 获取屏幕分辨率
     *
     * @return int[]
     */
    public static int[] getScreenResolution(Context context) {
        WindowManager windowManager = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
        return new int[]{mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels};
    }

    /**
     * 获取底部虚拟键盘的高度
     *
     * @return int
     */
    public static int getBottomKeyboardHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        try {
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics dm = new DisplayMetrics();
                @SuppressWarnings("rawtypes")
                Class c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
}