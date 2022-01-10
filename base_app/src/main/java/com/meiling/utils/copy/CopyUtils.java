package com.meiling.utils.copy;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 2022-01-10 11:56
 *
 * @author marisareimu
 */
public class CopyUtils {
    public interface Callback {
        void invalidContext();// 无效的上下文

        void emptyString();// 空的待复制字符串

        void emptyClipBoardService();// 剪贴板服务为空
    }

    public static void copyText(Context context, String text, Callback callback) {
        if (context == null) {
            if (callback != null) callback.invalidContext();
            return;
        }
        if (TextUtils.isEmpty(text)) {
            if (callback != null) callback.emptyString();
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            if (callback != null) callback.emptyClipBoardService();
            return;
        }
        clipboardManager.setText(text);
    }
}
