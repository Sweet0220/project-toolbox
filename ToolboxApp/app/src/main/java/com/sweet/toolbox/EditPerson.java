package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.SaveSystem;

public class EditPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        AppData data = SaveSystem.loadData(this);

        EditText lastName,firstName;
        lastName = findViewById(R.id.lastNameInput2);
        firstName = findViewById(R.id.firstNameInput2);
        String content = data.personArray[data.lastPersonInteraction].lastName;
        lastName.setText(content);
        content = data.personArray[data.lastPersonInteraction].firstName;
        firstName.setText(content);

        Button back = findViewById(R.id.backButton5);
        back.setOnClickListener(view -> finish());

        Button confirmButton = findViewById(R.id.completeEditPerson);
        confirmButton.setOnClickListener(view -> confirmEdit(lastName,firstName,data));
    }

    protected void confirmEdit(EditText lastName, EditText firstName, AppData data)
    {
        String content1 = lastName.getText().toString();
        String content2 = firstName.getText().toString();
        if(content1.length() == 0 || content2.length() == 0)
            Toast.makeText(EditPerson.this,"Exista un camp gol!",Toast.LENGTH_SHORT).show();
        else
        {
            data.personArray[data.lastPersonInteraction].lastName = content1;
            data.personArray[data.lastPersonInteraction].firstName = content2;
            SaveSystem.saveData(this,data);
            finish();
        }

    }
}