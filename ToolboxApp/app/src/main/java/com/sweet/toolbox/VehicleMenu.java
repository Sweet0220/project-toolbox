package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.SaveSystem;

import java.io.File;

public class VehicleMenu extends AppCompatActivity {

    private void setName(AppData data)
    {
        TextView name = findViewById(R.id.nameTitle);
        String fullName = data.personArray[data.lastPersonInteraction].firstName + " " + data.personArray[data.lastPersonInteraction].lastName;
        name.setText((CharSequence) fullName);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_menu);

        File path = getApplicationContext().getFilesDir();
        AppData data = SaveSystem.loadData(path);

        Button backButton = findViewById(R.id.backToPersonMenu);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setName(data);
    }
}