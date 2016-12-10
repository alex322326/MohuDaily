package com.yh.mohudaily.module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yh.mohudaily.R;


/**
 * Created by HiAll_yan on 2016/5/18.
 * 微信二维码对话框
 */
public class IconPickDialog extends Dialog {

    public IconPickDialog(Context context) {
        super(context);
    }

    public IconPickDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private View contentView;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public IconPickDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final IconPickDialog dialog = new IconPickDialog(context, R.style.iconPickDialog );
            dialog.setCanceledOnTouchOutside(true);
            View layout = inflater.inflate(R.layout.iconpick_dialog, null);
            TextView name = (TextView) layout.findViewById(R.id.pictures);
            TextView desc = (TextView) layout.findViewById(R.id.camera);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.addContentView(layout, new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button
            dialog.setContentView(layout);
            return dialog;
        }
    }


}