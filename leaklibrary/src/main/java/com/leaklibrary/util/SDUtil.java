package com.leaklibrary.util;

import android.content.Context;
import android.os.Environment;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public final class SDUtil {

    private static final String FILE_NAME = "LEAK.text";

    /**
     * @param context
     * @return
     */
    public static String getSdRoot(Context context) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILE_NAME;
        }

        return context.getFilesDir().toString() + "/" + FILE_NAME;
    }


}
