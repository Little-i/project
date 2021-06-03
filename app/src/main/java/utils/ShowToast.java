package utils;

import android.content.Context;
import android.widget.Toast;

public class ShowToast {
    private static Toast toast;

    public static void shortToast(Context context, String msg){
        if (toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void longToast(Context context, String msg){
        if (toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
