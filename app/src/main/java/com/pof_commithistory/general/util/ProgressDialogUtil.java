package com.pof_commithistory.general.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.pof_commithistory.R;

public class ProgressDialogUtil {

    public static ProgressDialog createProgressDialog(Context context) {

        ProgressDialog dialog = new ProgressDialog(context);

        try {
            dialog.show();
        } catch (Exception e) {

        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_custom_progress_dialog);

        return dialog;
    }

}
