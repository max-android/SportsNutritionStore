package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import ru.exampleopit111.sportsnutritionstore.R;


public class LocationPermission {

    public void requestPermission(Context context, FuncVoid funcVoid) {

//        ACCESS_COARSE_LOCATION - использование приблизительного определения местонахождения
// при помощи вышек сотовой связи или точек доступа Wi-Fi
//        ACCESS_FINE_LOCATION - точное определение местонахождения при помощи GPS

        if (Build.VERSION.SDK_INT >= 23) {
            //проверка - есть ли разрешение для определ местоположения,если нет,то объяснить почему необходимо
            // разрешение на повторный запрос
            int accessCoarsePermission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            //проверка - есть ли разрешение для определ местоположения,если нет,то объяснить почему необходимо
            // разрешение на повторный запрос
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED ||
                    accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                //случай отсутствия разрешения
                //объяснение и запрос на предоставление
                if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context,
                        Manifest.permission.ACCESS_FINE_LOCATION) &&
                        ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) context,
                                Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    //случай,если первый раз отказали,то дается объяснение при повторном запросе
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.permission_location_explanation);
                    builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        request(context);
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                } else {
                    //запрос на предоставление
                    request(context);
                }
            } else {
                //случай наличия разрешения
                funcVoid.action();
            }
        }
    }

    //Запрос разрешения
    private void request(Context context) {
        ActivityCompat.requestPermissions((AppCompatActivity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                Constants.LOCATION_PERMISSION_REQUEST_CODE);
    }

}
