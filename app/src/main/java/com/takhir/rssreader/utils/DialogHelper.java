package com.takhir.rssreader.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.takhir.rssreader.R;
import com.takhir.rssreader.WebViewActivity;

public class DialogHelper {

    public interface stringListener {
        void getString(String text);
    }


    public static AlertDialog showUrlDialog(Context context, final stringListener listener) {
        final EditText editText = new EditText(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle("Ведите url");
        builder.setView(editText);
        builder.setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String text = editText.getText().toString();
                                listener.getString(text);
                                dialog.cancel();
                            }
                        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public static AlertDialog showInfoChannel(final Context context, String title, final String link) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle("Канал");
        builder.setMessage(title);
        builder.setPositiveButton("Перейти на канал",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra("url", link);
                        context.startActivity(intent);
                    }
                });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
