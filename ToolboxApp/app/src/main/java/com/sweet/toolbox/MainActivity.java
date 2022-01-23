package com.sweet.toolbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    int editToggle7=0,deleteToggle7=0;

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
        editToggle7 = 0;
        deleteToggle7 = 0;
        super.onResume();
        fillMenuList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    finish();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.goToAddPerson);
        addButton.setOnClickListener(view -> openAddPersonActivity());

        Button deleteButton = findViewById(R.id.deletePersonButton);
        deleteButton.setOnClickListener(view -> {
            if(editToggle7 == 1)
                editToggle7 = 0;
            if(deleteToggle7 == 1)
            {
                deleteToggle7 = 0;
                fillMenuList();
            }
            else
            {
                deleteToggle7 = 1;
                fillMenuListDelete();
            }
        });


        Button editPersonButton = (Button) findViewById(R.id.editPersonButton);
        editPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deleteToggle7 == 1)
                    deleteToggle7 = 0;
                if(editToggle7 == 1)
                {
                    editToggle7 = 0;
                    fillMenuList();
                }
                else
                {
                    editToggle7 = 1;
                    fillMenuListEdit();
                }

            }
        });

        fillMenuList();
    }


    //region DynamicalCreation

    private void fillMenuListEdit()
    {
        LinearLayout mainLayout = findViewById(R.id.mainMenuLinearLayout);
        mainLayout.removeAllViews();
        AppData data = SaveSystem.loadData(this);

        for(int i=1;i<=data.numberOfPeople;i++)
        {
            ConstraintLayout newPersonFrame = createPersonFrame(i,data);


            ImageView personImage = createPersonImage(i,newPersonFrame);
            addConstraintsToImage(personImage,newPersonFrame);


            TextView nameText = createFullName(data,i);
            addConstraintsToFullName(nameText,personImage,newPersonFrame);


            TextView numberText = createNumberText(data,i);
            addConstraintsToNumber(nameText,numberText,newPersonFrame);

            ImageView editButton = createEditButton(newPersonFrame,i);

            mainLayout.addView(newPersonFrame);
        }
    }

    private void fillMenuListDelete()
    {
        LinearLayout mainLayout = findViewById(R.id.mainMenuLinearLayout);
        mainLayout.removeAllViews();
        AppData data = SaveSystem.loadData(this);

        for(int i=1;i<=data.numberOfPeople;i++)
        {
            ConstraintLayout newPersonFrame = createPersonFrame(i,data);


            ImageView personImage = createPersonImage(i,newPersonFrame);
            addConstraintsToImage(personImage,newPersonFrame);


            TextView nameText = createFullName(data,i);
            addConstraintsToFullName(nameText,personImage,newPersonFrame);


            TextView numberText = createNumberText(data,i);
            addConstraintsToNumber(nameText,numberText,newPersonFrame);

            createDeleteButton(newPersonFrame,i);

            mainLayout.addView(newPersonFrame);
        }
    }

    private void openConfirmPrompt(AppData data)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Stergeti persoana?");
        String content = "Nume: " + data.personArray[data.lastPersonInteraction].lastName + " " + data.personArray[data.lastPersonInteraction].firstName + "\nAtentie: nu se poate recupera!";
        builder.setMessage(content);
        builder.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePerson(data.lastPersonInteraction, data);
                    }
                });
        builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deletePerson(int id, AppData data)
    {
        for(int i=1;i<=data.numberOfVehicles;i++)
        {
            if(data.vehicleArray[i].personID == id && i == data.numberOfVehicles)
            {
                for(int j=1;j<=data.numberOfJobs;j++)
                {
                    if(data.jobArray[j].vehicleID == i && j == data.numberOfJobs)
                    {
                        data.numberOfJobs--;
                    }
                    else if(data.jobArray[j].vehicleID == i)
                    {
                        for(int k=j+1;k<=data.numberOfJobs;k++)
                        {
                            data.jobArray[k-1]=data.jobArray[k];
                            data.jobArray[k-1].id--;
                        }
                        j--;
                        data.numberOfJobs--;
                    }
                }
                data.numberOfVehicles--;
            }
            else if(data.vehicleArray[i].personID == id)
            {
                for(int j=1;j<=data.numberOfJobs;j++)
                {
                    if(data.jobArray[j].vehicleID == i && j == data.numberOfJobs)
                    {
                        data.numberOfJobs--;
                    }
                    else if(data.jobArray[j].vehicleID == i)
                    {
                        for(int k=j+1;k<=data.numberOfJobs;k++)
                        {
                            data.jobArray[k-1]=data.jobArray[k];
                            data.jobArray[k-1].id--;
                        }
                        j--;
                        data.numberOfJobs--;
                    }
                }
                for(int j=i+1;j<=data.numberOfVehicles;j++)
                {
                    data.vehicleArray[j-1] = data.vehicleArray[j];
                    data.vehicleArray[j-1].id--;
                }
                i--;
                data.numberOfVehicles--;
            }
        }

        if(id==data.numberOfPeople)
            data.numberOfPeople--;
        else
        {
            for(int i=id+1;i<=data.numberOfPeople;i++)
            {
                data.personArray[i-1] = data.personArray[i];
                data.personArray[i-1].id--;
            }
            data.numberOfPeople--;
        }
        SaveSystem.saveData(this,data);
        fillMenuList();
    }

    private void fillMenuList()
    {

        LinearLayout mainLayout = findViewById(R.id.mainMenuLinearLayout);
        mainLayout.removeAllViews();
        AppData data = SaveSystem.loadData(this);

        for(int i=1;i<=data.numberOfPeople;i++)
        {
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

    private ImageView createEditButton(ConstraintLayout personFrame, int id)
    {
        ImageView editButton = new ImageView(this);
        personFrame.addView(editButton);
        editButton.setId(16000+id);
        editButton.getLayoutParams().height = 90;
        editButton.getLayoutParams().width = 90;
        editButton.setImageResource(R.drawable.ic_edit_button_white);
        editButton.setClickable(true);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(personFrame);
        cs.connect(editButton.getId(), ConstraintSet.END, personFrame.getId(),ConstraintSet.END,20);
        cs.connect(editButton.getId(), ConstraintSet.TOP, personFrame.getId(),ConstraintSet.TOP);
        cs.connect(editButton.getId(), ConstraintSet.BOTTOM, personFrame.getId(),ConstraintSet.BOTTOM);
        cs.applyTo(personFrame);

        AppData data = SaveSystem.loadData(this);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.lastPersonInteraction = id;
                SaveSystem.saveData(MainActivity.this, data);
                openEditPersonActivity();
            }
        });

        return editButton;
    }

    private void createDeleteButton(ConstraintLayout personFrame, int id)
    {
        ImageView editButton = new ImageView(this);
        personFrame.addView(editButton);
        editButton.setId(16000+id);
        editButton.getLayoutParams().height = 90;
        editButton.getLayoutParams().width = 90;
        editButton.setImageResource(R.drawable.ic_delete_button_white);
        editButton.setClickable(true);
        ConstraintSet cs = new ConstraintSet();
        cs.clone(personFrame);
        cs.connect(editButton.getId(), ConstraintSet.END, personFrame.getId(),ConstraintSet.END,20);
        cs.connect(editButton.getId(), ConstraintSet.TOP, personFrame.getId(),ConstraintSet.TOP);
        cs.connect(editButton.getId(), ConstraintSet.BOTTOM, personFrame.getId(),ConstraintSet.BOTTOM);
        cs.applyTo(personFrame);

        AppData data = SaveSystem.loadData(this);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.lastPersonInteraction = id;
                SaveSystem.saveData(MainActivity.this, data);
                openConfirmPrompt(data);
                deleteToggle7 = 0;
                fillMenuList();
            }
        });
    }

    private void openEditPersonActivity()
    {
        Intent intent = new Intent(this,EditPerson.class);
        startActivity(intent);
    }

    private TextView createFullName(AppData data, int id)
    {
        TextView nameText = new TextView(this);
        nameText.setTag("name"+id);
        nameText.setId(200+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        nameText.setTypeface(tf);
        String fullName = data.personArray[id].lastName + " " + data.personArray[id].firstName;
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
                SaveSystem.saveData(MainActivity.this,data);
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