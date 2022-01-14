package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Person;
import com.sweet.toolbox.classes.SaveSystem;

import java.io.File;

public class AddPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);


        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Button addButton = findViewById(R.id.addPersonButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText lastNameInput = findViewById(R.id.lastNameInput);
                EditText firstNameInput = findViewById(R.id.firstNameInput);
                if(lastNameInput.getText().length() == 0 || firstNameInput.getText().length() == 0)
                {
                    Toast.makeText(AddPerson.this,"Exista un camp gol!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AppData data = SaveSystem.loadData(AddPerson.this);
                    data.numberOfPeople++;
                    data.personArray[data.numberOfPeople] = new Person(data.numberOfPeople, firstNameInput.getText().toString(), lastNameInput.getText().toString());
                    SaveSystem.saveData(AddPerson.this,data);
                    finish();
                }
            }
        });
    }
}