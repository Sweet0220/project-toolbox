package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
        AppData data = new AppData();
        data.numberOfPeople = 10;
        for(int i=1;i<=10;i++)
        {
            data.personArray[i]= new Person(i,"Mircea", "Nealcos");
        }
        data.personArray[2]= new Person(2,"Vasile", "Nealcos");
        data.personArray[3]= new Person(3,"Oana", "Nealcos");
        data.personArray[4]= new Person(4,"Anca", "Nealcos");
        data.numberOfVehicles = 3;
        data.vehicleArray[1] = new Vehicle(1,1, 2, "Mercedes-Benz", "CJ 15 BAD");
        data.vehicleArray[2] = new Vehicle(2,1, 1, "Fiat", "CJ 15 BOY");
        data.vehicleArray[3] = new Vehicle(3,2, 3, "Mercedes-Benz", "CJ 15 LOL");


        for(int i=1;i<=data.numberOfPeople;i++)
        {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainMenuLinearLayout);
            ConstraintLayout newPersonFrame = new ConstraintLayout(this);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            newPersonFrame.setId(i);
            newPersonFrame.setBackgroundResource(R.drawable.person_menu_frame);
            params.height = 210;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.rightMargin = 15;
            params.leftMargin = 15;
            params.topMargin = 10;
            params.bottomMargin = 10;
            newPersonFrame.setLayoutParams(params);



            ImageView personImage = new ImageView(this);
            newPersonFrame.addView(personImage);
            personImage.setTag("image"+i);
            personImage.setId(1000+i);
            personImage.setBackgroundResource(R.drawable.ic_person);
            personImage.getLayoutParams().height = 140;
            personImage.getLayoutParams().width = 140;
            ConstraintSet cs = new ConstraintSet();
            cs.clone(newPersonFrame);
            cs.connect(personImage.getId(),ConstraintSet.BOTTOM,newPersonFrame.getId(),ConstraintSet.BOTTOM,0);
            cs.connect(personImage.getId(),ConstraintSet.TOP,newPersonFrame.getId(),ConstraintSet.TOP,0);
            cs.connect(personImage.getId(),ConstraintSet.START,newPersonFrame.getId(),ConstraintSet.START,30);
            cs.applyTo(newPersonFrame);



            TextView nameText = new TextView(this);
            newPersonFrame.addView(nameText);
            nameText.setTag("name"+i);
            nameText.setId(200+i);
            Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
            nameText.setTypeface(tf);
            String fullName = data.personArray[i].firstName + " " + data.personArray[i].lastName;
            nameText.setText(fullName);


            mainLayout.addView(newPersonFrame);


            //Before you keep going, you'll clean this up first, alright? -_-
        }


    }
}