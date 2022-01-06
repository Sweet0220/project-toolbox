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

import java.io.File;

public class MainActivity extends AppCompatActivity {


    public void openVehicleMenuActivity()
    {
        Intent intent = new Intent(this, VehicleMenu.class);
        startActivity(intent);
    }

    public void openAddPersonActivity()
    {
        Intent intent = new Intent(this, AddPerson.class);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LinearLayout mainLayout = findViewById(R.id.mainMenuLinearLayout);
        mainLayout.removeAllViews();
        fillMenuList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPersonActivity();
            }
        });
        LinearLayout mainLayout = findViewById(R.id.mainMenuLinearLayout);
        mainLayout.removeAllViews();
        fillMenuList();
    }


    //region DynamicalCreation

    private void fillMenuList()
    {
        File path = getApplicationContext().getFilesDir();
        AppData data = SaveSystem.loadData(path);

        for(int i=1;i<=data.numberOfPeople;i++)
        {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainMenuLinearLayout);

            ConstraintLayout newPersonFrame = createPersonFrame(i,data);


            ImageView personImage = createPersonImage(i,newPersonFrame);
            addConstraintsToImage(personImage,newPersonFrame);


            TextView nameText = createFullName(data,i);
            addConstraintsToFullName(nameText,personImage,newPersonFrame);


            TextView numberText = createNumberText(data,i);
            addConstraintsToNumber(nameText,numberText,newPersonFrame);


            mainLayout.addView(newPersonFrame);
        }
    }

    private void addConstraintsToNumber(TextView nameText, TextView numberText, ConstraintLayout newPersonFrame)
    {
        newPersonFrame.addView(numberText);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(newPersonFrame);
        cs.connect(numberText.getId(),ConstraintSet.START,nameText.getId(),ConstraintSet.START,0);
        cs.connect(numberText.getId(),ConstraintSet.TOP,nameText.getId(),ConstraintSet.BOTTOM,15);
        cs.applyTo(newPersonFrame);
    }

    private void addConstraintsToFullName(TextView nameText, ImageView personImage, ConstraintLayout newPersonFrame)
    {
        newPersonFrame.addView(nameText);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(newPersonFrame);
        cs.connect(nameText.getId(),ConstraintSet.START,personImage.getId(),ConstraintSet.END,25);
        cs.connect(nameText.getId(),ConstraintSet.TOP,personImage.getId(),ConstraintSet.TOP,15);
        cs.applyTo(newPersonFrame);
    }

    private void addConstraintsToImage(ImageView personImage, ConstraintLayout newPersonFrame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(newPersonFrame);
        cs.connect(personImage.getId(),ConstraintSet.BOTTOM,newPersonFrame.getId(),ConstraintSet.BOTTOM,0);
        cs.connect(personImage.getId(),ConstraintSet.TOP,newPersonFrame.getId(),ConstraintSet.TOP,0);
        cs.connect(personImage.getId(),ConstraintSet.START,newPersonFrame.getId(),ConstraintSet.START,30);
        cs.applyTo(newPersonFrame);
    }

    private TextView createFullName(AppData data, int id)
    {
        TextView nameText = new TextView(this);
        nameText.setTag("name"+id);
        nameText.setId(200+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        nameText.setTypeface(tf);
        String fullName = data.personArray[id].firstName + " " + data.personArray[id].lastName;
        nameText.setText(fullName);
        nameText.setTextColor(Color.parseColor("#FBFAEC"));
        nameText.setTextSize(18);

        return nameText;
    }

    private TextView createNumberText(AppData data, int id)
    {
        TextView numberText = new TextView(this);

        numberText.setTag("number"+id);
        numberText.setId(500+id);

        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        numberText.setTypeface(tf);

        int vehicleCounter = 0;

        for(int i=1;i<=data.numberOfVehicles;i++)
            if(data.vehicleArray[i].personID==id)
                vehicleCounter++;

        String content = "Numar de autovehicule: " + vehicleCounter;
        numberText.setText(content);
        numberText.setTextColor(Color.parseColor("#757575"));
        numberText.setTextSize(15);

        return numberText;
    }

    private ConstraintLayout createPersonFrame(int id,AppData data)
    {
        ConstraintLayout newPersonFrame = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        newPersonFrame.setId(id);
        newPersonFrame.setBackgroundResource(R.drawable.person_menu_frame_controller);
        params.height = 210;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.rightMargin = 15;
        params.leftMargin = 15;
        params.topMargin = 10;
        params.bottomMargin = 10;
        newPersonFrame.setLayoutParams(params);
        newPersonFrame.setClickable(true);

        newPersonFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.lastPersonInteraction = id;
                File path = getApplicationContext().getFilesDir();
                SaveSystem.saveData(path,data);
                openVehicleMenuActivity();
            }
        });

        return newPersonFrame;
    }

    private ImageView createPersonImage(int id, ConstraintLayout newPersonFrame)
    {
        ImageView personImage = new ImageView(this);
        newPersonFrame.addView(personImage);
        personImage.setTag("image"+id);
        personImage.setId(1000+id);
        personImage.setBackgroundResource(R.drawable.ic_person);
        personImage.getLayoutParams().height = 140;
        personImage.getLayoutParams().width = 140;
        return personImage;
    }

    //endregion

}