package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Person;
import com.sweet.toolbox.classes.SaveSystem;
import com.sweet.toolbox.classes.Vehicle;

import java.io.File;

public class AddVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        Button backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        File path = getApplicationContext().getFilesDir();

        AppData data = SaveSystem.loadData(path);

        TextView nameTarget = findViewById(R.id.nameTarget);
        String content = data.personArray[data.lastPersonInteraction].firstName + " " + data.personArray[data.lastPersonInteraction].lastName;


        nameTarget.setText(content);

        ImageView smallCarButton, minivanButton, ivecoButton, ategoButton, scaniaButton, trailerButton;

        smallCarButton = findViewById(R.id.smallCarButton);
        minivanButton = findViewById(R.id.minivanButton);
        ivecoButton = findViewById(R.id.ivecoButton);
        ategoButton = findViewById(R.id.ategoButton);
        scaniaButton = findViewById(R.id.scaniaButton);
        trailerButton = findViewById(R.id.trailerButton);

        int[] selectedVehicle = new int[1];


        smallCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_white);
                minivanButton.setImageResource(R.drawable.ic_minivan_black);
                ivecoButton.setImageResource(R.drawable.ic_iveco_black);
                ategoButton.setImageResource(R.drawable.ic_atego_black);
                scaniaButton.setImageResource(R.drawable.ic_scania_black);
                trailerButton.setImageResource(R.drawable.ic_trailer_black);
                selectedVehicle[0] = 1;
            }
        });

        minivanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_black);
                minivanButton.setImageResource(R.drawable.ic_minivan_white);
                ivecoButton.setImageResource(R.drawable.ic_iveco_black);
                ategoButton.setImageResource(R.drawable.ic_atego_black);
                scaniaButton.setImageResource(R.drawable.ic_scania_black);
                trailerButton.setImageResource(R.drawable.ic_trailer_black);
                selectedVehicle[0] = 2;
            }
        });

        ivecoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_black);
                minivanButton.setImageResource(R.drawable.ic_minivan_black);
                ivecoButton.setImageResource(R.drawable.ic_iveco_white);
                ategoButton.setImageResource(R.drawable.ic_atego_black);
                scaniaButton.setImageResource(R.drawable.ic_scania_black);
                trailerButton.setImageResource(R.drawable.ic_trailer_black);
                selectedVehicle[0] = 3;
            }
        });

        ategoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_black);
                minivanButton.setImageResource(R.drawable.ic_minivan_black);
                ivecoButton.setImageResource(R.drawable.ic_iveco_black);
                ategoButton.setImageResource(R.drawable.ic_atego_white);
                scaniaButton.setImageResource(R.drawable.ic_scania_black);
                trailerButton.setImageResource(R.drawable.ic_trailer_black);
                selectedVehicle[0] = 4;
            }
        });

        scaniaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_black);
                minivanButton.setImageResource(R.drawable.ic_minivan_black);
                ivecoButton.setImageResource(R.drawable.ic_iveco_black);
                ategoButton.setImageResource(R.drawable.ic_atego_black);
                scaniaButton.setImageResource(R.drawable.ic_scania_white);
                trailerButton.setImageResource(R.drawable.ic_trailer_black);
                selectedVehicle[0] = 5;
            }
        });

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smallCarButton.setImageResource(R.drawable.ic_delorean_black);
                minivanButton.setImageResource(R.drawable.ic_minivan_black);
                ivecoButton.setImageResource(R.drawable.ic_iveco_black);
                ategoButton.setImageResource(R.drawable.ic_atego_black);
                scaniaButton.setImageResource(R.drawable.ic_scania_black);
                trailerButton.setImageResource(R.drawable.ic_trailer_white);
                selectedVehicle[0] = 6;
            }
        });

        EditText brandInput = findViewById(R.id.brandInput), licensePlateInput = findViewById(R.id.licensePlateInput);

        Button addVehicleButton = findViewById(R.id.addVehicleButton);
        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brandInput.getText().length() == 0 || licensePlateInput.getText().length() == 0)
                {
                    Toast.makeText(AddVehicle.this,"Exista un camp gol!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    File path = getApplicationContext().getFilesDir();
                    data.numberOfVehicles++;
                    data.vehicleArray[data.numberOfVehicles] = new Vehicle(data.numberOfVehicles, data.lastPersonInteraction, selectedVehicle[0], brandInput.getText().toString(), licensePlateInput.getText().toString());
                    SaveSystem.saveData(path,data);
                    finish();
                }
            }
        });

    }
}