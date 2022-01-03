package com.sweet.toolbox.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveSystem {

    public static void saveData(File path, AppData data) {
        String fileName = "data.bin";
        try {

            FileOutputStream stream = new FileOutputStream(new File(path,fileName));
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);

            objectStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppData loadData(File path) {
        String fileName = "data.bin";
        try {
            FileInputStream stream = new FileInputStream(new File(path,fileName));
            ObjectInputStream objectStream = new ObjectInputStream(stream);
            AppData data;
            data = (AppData) objectStream.readObject();
            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
