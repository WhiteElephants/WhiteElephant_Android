package rawe.gordon.com.fruitmarketclient.generals.dialogs.warning;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/8/31.
 */
public class DialogHelper {
    public static Dialog createTwoChoiceDialog(Activity activity, String title, String content, String sureText, String cancelText, final TwoChoiceListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.customDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_two_warning);
        TextView titleView = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView contentView = (TextView) dialog.findViewById(R.id.dialog_content);
        TextView sureView = (TextView) dialog.findViewById(R.id.dialog_sure);
        TextView cancelView = (TextView) dialog.findViewById(R.id.dialog_cancel);
        titleView.setText(title);
        contentView.setText(content);
        sureView.setText(sureText);
        cancelView.setText(cancelText);
        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onYes();
                        }
                    }, 500);
                }
                dialog.dismiss();
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onNo();
                        }
                    }, 500);
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public interface TwoChoiceListener {
        void onYes();

        void onNo();
    }

}
