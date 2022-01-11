package com.meiling.utils.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印工具
 *
 * 2022-01-11 13:56
 *
 * @author marisareimu
 */
public class LogCatUtil {
    private static String TAG = "AndroidRuntime";
    private static boolean isDebug = false;
    private static final int LENGTH_LIMIT = 2500;

    public static void setTagAndDebug(String tag, boolean debug) {
        TAG = tag;
        isDebug = debug;
    }

    public static void e(String msg) {
        if (!isDebug) return;
        if (TextUtils.isEmpty(msg)) return;
        if (msg.length() > LENGTH_LIMIT) {
            int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = LENGTH_LIMIT * (i + 1);
                if (max >= msg.length()) {
                    Log.e(TAG, msg.substring(LENGTH_LIMIT * i));
                } else {
                    Log.e(TAG, msg.substring(LENGTH_LIMIT * i, max));
                }
            }
        } else {
            Log.e(TAG, msg.toString());
        }
    }

    public static void w(String msg) {
        if (!isDebug) return;
        if (TextUtils.isEmpty(msg)) return;
        if (msg.length() > LENGTH_LIMIT) {
            int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = LENGTH_LIMIT * (i + 1);
                if (max >= msg.length()) {
                    Log.w(TAG, msg.substring(LENGTH_LIMIT * i));
                } else {
                    Log.w(TAG, msg.substring(LENGTH_LIMIT * i, max));
                }
            }
        } else {
            Log.w(TAG, msg.toString());
        }
    }

    public static void i(String msg) {
        if (!isDebug) return;
        if (TextUtils.isEmpty(msg)) return;
        if (msg.length() > LENGTH_LIMIT) {
            int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = LENGTH_LIMIT * (i + 1);
                if (max >= msg.length()) {
                    Log.i(TAG, msg.substring(LENGTH_LIMIT * i));
                } else {
                    Log.i(TAG, msg.substring(LENGTH_LIMIT * i, max));
                }
            }
        } else {
            Log.i(TAG, msg.toString());
        }
    }

    public static void d(String msg) {
        if (!isDebug) return;
        if (TextUtils.isEmpty(msg)) return;
        if (msg.length() > LENGTH_LIMIT) {
            int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = LENGTH_LIMIT * (i + 1);
                if (max >= msg.length()) {
                    Log.d(TAG, msg.substring(LENGTH_LIMIT * i));
                } else {
                    Log.d(TAG, msg.substring(LENGTH_LIMIT * i, max));
                }
            }
        } else {
            Log.d(TAG, msg.toString());
        }
    }
}
