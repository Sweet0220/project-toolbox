package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.SaveSystem;

public class EditVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        AppData data = SaveSystem.loadData(this);

        Button back = findViewById(R.id.backButton6);
        back.setOnClickListener(view -> finish());

        populateFields(data);

        Button confirm = findViewById(R.id.confirmVehicleChange);
        confirm.setOnClickListener(view -> {
            EditText brand, license;
            brand = findViewById(R.id.brandInput2);
            license = findViewById(R.id.licensePlateInput2);
            String content = brand.getText().toString();
            data.vehicleArray[data.lastVehicleInteraction].brand = content;
            content = license.getText().toString();
            data.vehicleArray[data.lastVehicleInteraction].licensePlate = content;
            SaveSystem.saveData(EditVehicle.this,data);
            finish();
        });

    }

    private void populateFields(AppData data)
    {
        EditText brand, license;
        brand = findViewById(R.id.brandInput2);
        license = findViewById(R.id.licensePlateInput2);
        String content = data.vehicleArray[data.lastVehicleInteraction].brand;
        brand.setText(content);
        content = data.vehicleArray[data.lastVehicleInteraction].licensePlate;
        license.setText(content);
        ImageView deLorean, minivan, iveco, atego, scania, trailer;
        deLorean = findViewById(R.id.smallCarButton1);
        minivan = findViewById(R.id.minivanButton1);
        iveco = findViewById(R.id.ivecoButton1);
        atego = findViewById(R.id.ategoButton1);
        scania = findViewById(R.id.scaniaButton1);
        trailer = findViewById(R.id.trailerButton1);

        switch(data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle)
        {
            case 1:
                deLorean.setImageResource(R.drawable.ic_delorean_white);
                break;
            case 2:
                minivan.setImageResource(R.drawable.ic_minivan_white);
                break;
            case 3:
                iveco.setImageResource(R.drawable.ic_iveco_white);
                break;
            case 4:
                atego.setImageResource(R.drawable.ic_atego_white);
                break;
            case 5:
                scania.setImageResource(R.drawable.ic_scania_white);
                break;
            case 6:
                trailer.setImageResource(R.drawable.ic_trailer_white);
                break;
        }

        deLorean.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 1;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_white);
            minivan.setImageResource(R.drawable.ic_minivan_black);
            iveco.setImageResource(R.drawable.ic_iveco_black);
            atego.setImageResource(R.drawable.ic_atego_black);
            scania.setImageResource(R.drawable.ic_scania_black);
            trailer.setImageResource(R.drawable.ic_trailer_black);
        });

        minivan.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 2;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_black);
            minivan.setImageResource(R.drawable.ic_minivan_white);
            iveco.setImageResource(R.drawable.ic_iveco_black);
            atego.setImageResource(R.drawable.ic_atego_black);
            scania.setImageResource(R.drawable.ic_scania_black);
            trailer.setImageResource(R.drawable.ic_trailer_black);
        });

        iveco.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 3;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_black);
            minivan.setImageResource(R.drawable.ic_minivan_black);
            iveco.setImageResource(R.drawable.ic_iveco_white);
            atego.setImageResource(R.drawable.ic_atego_black);
            scania.setImageResource(R.drawable.ic_scania_black);
            trailer.setImageResource(R.drawable.ic_trailer_black);
        });

        atego.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 4;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_black);
            minivan.setImageResource(R.drawable.ic_minivan_black);
            iveco.setImageResource(R.drawable.ic_iveco_black);
            atego.setImageResource(R.drawable.ic_atego_white);
            scania.setImageResource(R.drawable.ic_scania_black);
            trailer.setImageResource(R.drawable.ic_trailer_black);
        });

        scania.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 5;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_black);
            minivan.setImageResource(R.drawable.ic_minivan_black);
            iveco.setImageResource(R.drawable.ic_iveco_black);
            atego.setImageResource(R.drawable.ic_atego_black);
            scania.setImageResource(R.drawable.ic_scania_white);
            trailer.setImageResource(R.drawable.ic_trailer_black);
        });

        trailer.setOnClickListener(view -> {
            data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle = 6;
            SaveSystem.saveData(EditVehicle.this,data);
            deLorean.setImageResource(R.drawable.ic_delorean_black);
            minivan.setImageResource(R.drawable.ic_minivan_black);
            iveco.setImageResource(R.drawable.ic_iveco_black);
            atego.setImageResource(R.drawable.ic_atego_black);
            scania.setImageResource(R.drawable.ic_scania_black);
            trailer.setImageResource(R.drawable.ic_trailer_white);
        });

    }
}