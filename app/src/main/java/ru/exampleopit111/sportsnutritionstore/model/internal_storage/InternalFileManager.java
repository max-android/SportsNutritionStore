package ru.exampleopit111.sportsnutritionstore.model.internal_storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created Максим on 19.06.2018.
 * Copyright © Max
 */
public class InternalFileManager {

    private static final String IMAGE_PROFILE = "image_profile";

    private Context context;

    public InternalFileManager(Context context) {
        this.context = context;
    }

    public void writeFile(String url) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(IMAGE_PROFILE, Context.MODE_PRIVATE)));
            // пишем данные
            bw.write(url);
            // закрываем поток
            bw.close();
            // Log.d("TAG", "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        String url = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(IMAGE_PROFILE)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {

                url = url + str;
                //  Log.d("TAG", url);
                // return str;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public boolean checkExistFile() {
        if (context.fileList().length != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteFile() {
        context.deleteFile(IMAGE_PROFILE);
    }
}
