package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Job;
import com.sweet.toolbox.classes.SaveSystem;

import java.io.File;

public class AddJob extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        Button backButton = findViewById(R.id.backButton3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AppData data = SaveSystem.loadData(this);

        TextView licenseTarget = findViewById(R.id.licenseTarget);
        String content = "Lucrare noua pentru " + data.vehicleArray[data.lastVehicleInteraction].licensePlate;
        licenseTarget.setText(content);

        final int[] brakeCheck = {0};
        final int[] engineCheck = {0};
        final int[] suspensionCheck = {0};
        final int[] otherCheck = {0};
        ImageView brakeButton, engineButton, suspensionButton, otherButton;
        brakeButton = findViewById(R.id.addBrakeIcon);
        engineButton = findViewById(R.id.addEngineButton);
        suspensionButton = findViewById(R.id.addSuspensionButton);
        otherButton = findViewById(R.id.addOtherButton);

        //region iconTriggers
        brakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (brakeCheck[0])
                {
                    case 0:
                        brakeCheck[0] = 1;
                        brakeButton.setImageResource(R.drawable.ic_brake_white);
                        break;
                    case 1:
                        brakeCheck[0] = 0;
                        brakeButton.setImageResource(R.drawable.ic_brake_black);
                        break;
                }
            }
        });

        engineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(engineCheck[0])
                {
                    case 0:
                        engineCheck[0] = 1;
                        engineButton.setImageResource(R.drawable.ic_engine_white);
                        break;
                    case 1:
                        engineCheck[0] = 0;
                        engineButton.setImageResource(R.drawable.ic_engine_black);
                        break;
                }
            }
        });

        suspensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(suspensionCheck[0])
                {
                    case 0:
                        suspensionCheck[0] = 1;
                        suspensionButton.setImageResource(R.drawable.ic_suspension_white);
                        break;
                    case 1:
                        suspensionCheck[0] = 0;
                        suspensionButton.setImageResource(R.drawable.ic_suspension_black);
                        break;
                }
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(otherCheck[0])
                {
                    case 0:
                        otherCheck[0] = 1;
                        otherButton.setImageResource(R.drawable.ic_others_white);
                        break;
                    case 1:
                        otherCheck[0] = 0;
                        otherButton.setImageResource(R.drawable.ic_others_black);
                        break;
                }
            }
        });
        //endregion

        Button addJob = findViewById(R.id.addJobButton);
        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText dayInput = findViewById(R.id.dayInput);
                EditText monthInput = findViewById(R.id.monthInput);
                EditText yearInput = findViewById(R.id.yearInput);
                EditText costInput = findViewById(R.id.costInput);
                EditText detailsInput = findViewById(R.id.detailsInput);

                if(dayInput.getText().toString().equals("")|| monthInput.getText().toString().equals("")
                || yearInput.getText().toString().equals("") || costInput.getText().toString().equals("") || detailsInput.getText().toString().equals(""))
                {
                    Toast.makeText(AddJob.this,"Exista un camp gol!",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(monthInput.getText().toString()) == 2 && Integer.parseInt(dayInput.getText().toString()) > 29)
                {
                    Toast.makeText(AddJob.this,"Luna februarie nu are atatea zile..",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(dayInput.getText().toString())<1 || Integer.parseInt(dayInput.getText().toString())>31)
                {
                    Toast.makeText(AddJob.this,"Valoarea zilei invalida!",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(monthInput.getText().toString())<1 || Integer.parseInt(monthInput.getText().toString())>12)
                {
                    Toast.makeText(AddJob.this,"Valoarea lunii invalida!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int cost, d, m, y;
                    cost = Integer.parseInt(costInput.getText().toString());
                    d = Integer.parseInt(dayInput.getText().toString());
                    m = Integer.parseInt(monthInput.getText().toString());
                    y = Integer.parseInt(yearInput.getText().toString());
                    String detailsText = detailsInput.getText().toString();
                    data.numberOfJobs++;
                    data.jobArray[data.numberOfJobs] = new Job(data.numberOfJobs, data.lastVehicleInteraction, d, m, y, brakeCheck[0], engineCheck[0], suspensionCheck[0], otherCheck[0],detailsText,cost,0);
                    SaveSystem.saveData(AddJob.this,data);
                    finish();
                }
            }
        });
    }
}