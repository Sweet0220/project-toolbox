package com.sweet.toolbox.classes;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveSystem {

    public static void saveData(Context context,AppData data) {
        File root = new File(context.getExternalFilesDir(null).getAbsolutePath());
        File dir = new File(root+"/date_toolbox");
        if(!dir.exists())
            dir.mkdirs();
        String fileName = "toolbox_data.bin";
        File file = new File(dir,fileName);
        try {

            FileOutputStream stream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);

            objectStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppData loadData(Context context) {
        File root = new File(context.getExternalFilesDir(null).getAbsolutePath());
        File dir = new File(root + "/date_toolbox");
        if(!dir.exists())
            dir.mkdir();
        String fileName = "toolbox_data.bin";
        File file = new File(dir,fileName);
        try {
            FileInputStream stream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(stream);
            AppData data;
            data = (AppData) objectStream.readObject();
            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new AppData();
        }
    }

}
