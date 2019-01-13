package com.moallem.stu.utilities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.moallem.stu.R;
import com.moallem.stu.data.PrefsHelper;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public static void hideAndroidUI(Activity context){
        if (context != null) {
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static String getStringCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate.replaceAll("-", "").replaceAll(" ", "")
                .replaceAll(":", "").replaceAll("\\.", "");
    }

    public static String getUserType(Context context){
        return PrefsHelper.getInstance(context).getUserType();
    }

    public static String getCurrentUserId(){
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser().getUid();
    }


    public static String CalculateDigest(String publicKey, String message, String privateKey)
    {
        try
        {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] digest = sha256_HMAC.doFinal(message.getBytes());
            String result = publicKey + ":" + new BigInteger(1, digest).toString(16);
            return result;
        }

        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static String getDeviceLanguage(Context context){
        return context.getResources().getString(R.string.deviceLangauge);
    }


}