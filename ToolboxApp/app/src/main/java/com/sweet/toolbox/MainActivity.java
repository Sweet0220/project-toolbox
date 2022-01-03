package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Job;
import com.sweet.toolbox.classes.Person;
import com.sweet.toolbox.classes.SaveSystem;
import com.sweet.toolbox.classes.Vehicle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}