package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.SaveSystem;
import com.sweet.toolbox.classes.Vehicle;

import org.w3c.dom.Text;

import java.io.File;

public class VehicleMenu extends AppCompatActivity {

    private void setName(AppData data)
    {
        TextView name = findViewById(R.id.nameTitle);
        String fullName = data.personArray[data.lastPersonInteraction].firstName + " " + data.personArray[data.lastPersonInteraction].lastName;
        name.setText((CharSequence) fullName);
    }

    public void openAddVehicleActivity()
    {
        Intent intent = new Intent(this, AddVehicle.class);
        startActivity(intent);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        LinearLayout mainLayout = findViewById(R.id.vehicleMenuLinearLayout);
        mainLayout.removeAllViews();
        fillVehicleList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_menu);

        Button addVehicle = findViewById(R.id.addVehicle);
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddVehicleActivity();
            }
        });

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

        LinearLayout vehicleLayout = findViewById(R.id.vehicleMenuLinearLayout);
        vehicleLayout.removeAllViews();

        fillVehicleList();
    }

    //region DynamicalCreation

    private void fillVehicleList()
    {
        File path = getApplicationContext().getFilesDir();
        AppData data = SaveSystem.loadData(path);
        LinearLayout vehicleLayout = findViewById(R.id.vehicleMenuLinearLayout);

        for(int i=1;i<=data.numberOfVehicles;i++)
        {
            if(data.vehicleArray[i].personID == data.lastPersonInteraction)
            {
                ConstraintLayout vehicleFrame = createVehicleFrame(i,data);

                ImageView vehicleImage = createVehicleImage(i,vehicleFrame,data);
                addConstraintsToImage(vehicleImage,vehicleFrame);

                TextView brand = createBrandText(data,i);
                addConstraintsToBrand(brand,vehicleImage,vehicleFrame);

                TextView licensePlate = createLicenseText(data,i);
                addConstraintsToLicensePlate(vehicleFrame,licensePlate,brand);

                TextView numberText = createNumberText(data, i);
                addConstraintsToNumberText(vehicleFrame,licensePlate,numberText);

                vehicleLayout.addView(vehicleFrame);
            }
        }
    }

    private void addConstraintsToNumberText(ConstraintLayout vehicleFrame, TextView licensePlate, TextView numberText)
    {
        vehicleFrame.addView(numberText);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(vehicleFrame);
        cs.connect(numberText.getId(),ConstraintSet.START,licensePlate.getId(),ConstraintSet.START,0);
        cs.connect(numberText.getId(),ConstraintSet.TOP,licensePlate.getId(),ConstraintSet.BOTTOM,5);
        cs.applyTo(vehicleFrame);
    }

    private void addConstraintsToLicensePlate(ConstraintLayout vehicleFrame, TextView licensePlate, TextView brand)
    {
        vehicleFrame.addView(licensePlate);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(vehicleFrame);
        cs.connect(licensePlate.getId(),ConstraintSet.START,brand.getId(),ConstraintSet.START,0);
        cs.connect(licensePlate.getId(),ConstraintSet.TOP,brand.getId(),ConstraintSet.BOTTOM,5);
        cs.applyTo(vehicleFrame);
    }

    private void addConstraintsToBrand(TextView brand, ImageView vehicleImage, ConstraintLayout vehicleFrame)
    {
        vehicleFrame.addView(brand);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(vehicleFrame);
        cs.connect(brand.getId(),ConstraintSet.START,vehicleImage.getId(),ConstraintSet.END,35);
        cs.connect(brand.getId(),ConstraintSet.TOP,vehicleImage.getId(),ConstraintSet.TOP,15);
        cs.applyTo(vehicleFrame);
    }

    private void addConstraintsToImage(ImageView vehicleImage, ConstraintLayout vehicleFrame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(vehicleFrame);
        cs.connect(vehicleImage.getId(),ConstraintSet.BOTTOM,vehicleFrame.getId(),ConstraintSet.BOTTOM,0);
        cs.connect(vehicleImage.getId(),ConstraintSet.TOP,vehicleFrame.getId(),ConstraintSet.TOP,0);
        cs.connect(vehicleImage.getId(),ConstraintSet.START,vehicleFrame.getId(),ConstraintSet.START,20);
        cs.applyTo(vehicleFrame);
    }

    private TextView createBrandText(AppData data, int id)
    {
        TextView brand = new TextView(this);
        brand.setTag("name"+id);
        brand.setId(200+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        brand.setTypeface(tf);
        String brandName = data.vehicleArray[id].brand;
        brand.setText(brandName);
        brand.setTextColor(Color.parseColor("#FBFAEC"));
        brand.setTextSize(20);

        return brand;
    }

    private ConstraintLayout createVehicleFrame(int id, AppData data)
    {
        ConstraintLayout vehicleFrame = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vehicleFrame.setId(id);
        vehicleFrame.setBackgroundResource(R.drawable.person_menu_frame_controller);
        params.height = 230;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.rightMargin = 15;
        params.leftMargin = 15;
        params.topMargin = 10;
        params.bottomMargin = 10;
        vehicleFrame.setLayoutParams(params);
        vehicleFrame.setClickable(true);

        vehicleFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.lastVehicleInteraction = id;
                File path = getApplicationContext().getFilesDir();
                SaveSystem.saveData(path,data);
                openJobMenuActivity();
            }
        });

        return vehicleFrame;
    }

    private void openJobMenuActivity()
    {
        Intent intent = new Intent(this, JobMenu.class);
        startActivity(intent);
    }

    private TextView createLicenseText(AppData data, int id)
    {
        TextView licensePlate = new TextView(this);

        licensePlate.setTag("number"+id);
        licensePlate.setId(500+id);

        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        licensePlate.setTypeface(tf);

        String content = data.vehicleArray[id].licensePlate;
        licensePlate.setText(content);
        licensePlate.setTextColor(Color.parseColor("#757575"));
        licensePlate.setTextSize(15);

        return licensePlate;
    }

    private ImageView createVehicleImage(int id, ConstraintLayout vehicleFrame, AppData data)
    {
        ImageView vehicleImage = new ImageView(this);
        vehicleFrame.addView(vehicleImage);
        vehicleImage.setTag("image"+id);
        vehicleImage.setId(1000+id);
        vehicleImage.getLayoutParams().height = 180;
        vehicleImage.getLayoutParams().width = 180;
        switch (data.vehicleArray[id].typeOfVehicle)
        {
            case 1:
                vehicleImage.setBackgroundResource(R.drawable.ic_delorean_white);
                break;
            case 2:
                vehicleImage.setBackgroundResource(R.drawable.ic_minivan_white);
                break;
            case 3:
                vehicleImage.setBackgroundResource(R.drawable.ic_iveco_white);
                break;
            case 4:
                vehicleImage.setBackgroundResource(R.drawable.ic_atego_white);
                break;
            case 5:
                vehicleImage.setBackgroundResource(R.drawable.ic_scania_white);
                break;
            case 6:
                vehicleImage.setBackgroundResource(R.drawable.ic_trailer_white);
                break;
        }
        return vehicleImage;
    }

    private TextView createNumberText(AppData data, int id)
    {
        TextView numberText = new TextView(this);

        numberText.setTag("number"+id);
        numberText.setId(1200+id);

        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        numberText.setTypeface(tf);

        int jobCounter = 0;

        for(int i=1;i<=data.numberOfJobs;i++)
            if(data.jobArray[i].vehicleID==id)
                jobCounter++;

        String content = "Numar de lucrari: " + jobCounter;
        numberText.setText(content);
        numberText.setTextColor(Color.parseColor("#757575"));
        numberText.setTextSize(15);

        return numberText;
    }

    //endregion
}