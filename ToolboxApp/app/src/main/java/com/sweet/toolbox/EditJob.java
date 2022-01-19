package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Job;
import com.sweet.toolbox.classes.SaveSystem;

public class EditJob extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);

        Button back = findViewById(R.id.backButton7);
        back.setOnClickListener(view -> {finish();});

        AppData data = SaveSystem.loadData(this);
        populateFields(data);
        makeButtonsInteractive(data);
        Button confirm = findViewById(R.id.confirmJobEdit);
        confirm.setOnClickListener(view -> {
            EditText dayInput = findViewById(R.id.dayInput2);
            EditText monthInput = findViewById(R.id.monthInput2);
            EditText yearInput = findViewById(R.id.yearInput2);
            EditText costInput = findViewById(R.id.costInput2);
            EditText detailsInput = findViewById(R.id.detailsInput2);

            if(dayInput.getText().toString().equals("")|| monthInput.getText().toString().equals("")
                    || yearInput.getText().toString().equals("") || costInput.getText().toString().equals("") || detailsInput.getText().toString().equals(""))
            {
                Toast.makeText(EditJob.this,"Exista un camp gol!",Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(monthInput.getText().toString()) == 2 && Integer.parseInt(dayInput.getText().toString()) > 29)
            {
                Toast.makeText(EditJob.this,"Luna februarie nu are atatea zile..",Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(dayInput.getText().toString())<1 || Integer.parseInt(dayInput.getText().toString())>31)
            {
                Toast.makeText(EditJob.this,"Valoarea zilei invalida!",Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(monthInput.getText().toString())<1 || Integer.parseInt(monthInput.getText().toString())>12)
            {
                Toast.makeText(EditJob.this,"Valoarea lunii invalida!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                int d, m, y;
                double cost;
                cost = Double.parseDouble(costInput.getText().toString());
                d = Integer.parseInt(dayInput.getText().toString());
                m = Integer.parseInt(monthInput.getText().toString());
                y = Integer.parseInt(yearInput.getText().toString());
                String detailsText = detailsInput.getText().toString();
                data.jobArray[data.lastJobInteraction].day = d;
                data.jobArray[data.lastJobInteraction].month = m;
                data.jobArray[data.lastJobInteraction].year = y;
                data.jobArray[data.lastJobInteraction].cost = cost;
                data.jobArray[data.lastJobInteraction].jobDetails = detailsText;
                SaveSystem.saveData(EditJob.this,data);
                finish();
            }
        });
    }

    private void populateFields(AppData data)
    {
        EditText day,month,year,details,cost;
        day = findViewById(R.id.dayInput2);
        month = findViewById(R.id.monthInput2);
        year = findViewById(R.id.yearInput2);
        details = findViewById(R.id.detailsInput2);
        cost = findViewById(R.id.costInput2);
        String content = ""+ data.jobArray[data.lastJobInteraction].day;
        day.setText(content);
        content = "" + data.jobArray[data.lastJobInteraction].month;
        month.setText(content);
        content = "" + data.jobArray[data.lastJobInteraction].year;
        year.setText(content);
        content = data.jobArray[data.lastJobInteraction].jobDetails;
        details.setText(content);
        content = "" + data.jobArray[data.lastJobInteraction].cost;
        cost.setText(content);

        ImageView engine,susp,brake,other;
        engine = findViewById(R.id.addEngineButton2);
        brake = findViewById(R.id.addBrakeIcon2);
        susp = findViewById(R.id.addSuspensionButton2);
        other = findViewById(R.id.addOtherButton2);

        switch(data.jobArray[data.lastJobInteraction].brakeIcon)
        {
            case 1:
                brake.setImageResource(R.drawable.ic_brake_white);
                break;
            case 0:
                brake.setImageResource(R.drawable.ic_brake_black);
                break;
        }

        switch(data.jobArray[data.lastJobInteraction].engineIcon)
        {
            case 1:
                engine.setImageResource(R.drawable.ic_engine_white);
                break;
            case 0:
                engine.setImageResource(R.drawable.ic_engine_black);
                break;
        }

        switch(data.jobArray[data.lastJobInteraction].suspensionIcon)
        {
            case 1:
                susp.setImageResource(R.drawable.ic_suspension_white);
                break;
            case 0:
                susp.setImageResource(R.drawable.ic_suspension_black);
                break;
        }

        switch(data.jobArray[data.lastJobInteraction].otherIcon)
        {
            case 1:
                other.setImageResource(R.drawable.ic_others_white);
                break;
            case 0:
                other.setImageResource(R.drawable.ic_others_black);
                break;
        }

        ImageView statusButton = findViewById(R.id.statusButton);
        TextView statusText = findViewById(R.id.statusText2);

        switch(data.jobArray[data.lastJobInteraction].status)
        {
            case 1:
                statusButton.setImageResource(R.drawable.ic_cash_black);
                statusText.setText("platit");
                break;
            case 0:
                statusButton.setImageResource(R.drawable.ic_cash);
                statusText.setText("neplatit");
                break;
        }
    }

    private void makeButtonsInteractive(AppData data)
    {
        ImageView brake,engine,susp,other,status;
        TextView statusText;
        brake = findViewById(R.id.addBrakeIcon2);
        engine = findViewById(R.id.addEngineButton2);
        susp = findViewById(R.id.addSuspensionButton2);
        other = findViewById(R.id.addOtherButton2);
        status = findViewById(R.id.statusButton);
        statusText = findViewById(R.id.statusText2);
        brake.setOnClickListener(view -> {
            switch(data.jobArray[data.lastJobInteraction].brakeIcon)
            {
                case 1:
                    brake.setImageResource(R.drawable.ic_brake_black);
                    data.jobArray[data.lastJobInteraction].brakeIcon = 0;
                    break;
                case 0:
                    brake.setImageResource(R.drawable.ic_brake_white);
                    data.jobArray[data.lastJobInteraction].brakeIcon = 1;
                    break;

            }
        });
        engine.setOnClickListener(view -> {
            switch(data.jobArray[data.lastJobInteraction].engineIcon)
            {
                case 1:
                    engine.setImageResource(R.drawable.ic_engine_black);
                    data.jobArray[data.lastJobInteraction].engineIcon = 0;
                    break;
                case 0:
                    engine.setImageResource(R.drawable.ic_engine_white);
                    data.jobArray[data.lastJobInteraction].engineIcon = 1;
                    break;

            }
        });
        susp.setOnClickListener(view -> {
            switch(data.jobArray[data.lastJobInteraction].suspensionIcon)
            {
                case 1:
                    susp.setImageResource(R.drawable.ic_suspension_black);
                    data.jobArray[data.lastJobInteraction].suspensionIcon = 0;
                    break;
                case 0:
                    susp.setImageResource(R.drawable.ic_suspension_white);
                    data.jobArray[data.lastJobInteraction].suspensionIcon = 1;
                    break;
            }
        });
        other.setOnClickListener(view -> {
            switch(data.jobArray[data.lastJobInteraction].otherIcon)
            {
                case 1:
                    other.setImageResource(R.drawable.ic_others_black);
                    data.jobArray[data.lastJobInteraction].otherIcon = 0;
                    break;
                case 0:
                    other.setImageResource(R.drawable.ic_others_white);
                    data.jobArray[data.lastJobInteraction].otherIcon = 1;
                    break;
            }
        });
        status.setOnClickListener(view -> {
            switch(data.jobArray[data.lastJobInteraction].status)
            {
                case 1:
                    status.setImageResource(R.drawable.ic_cash);
                    statusText.setText("neplatit");
                    data.jobArray[data.lastJobInteraction].status = 0;
                    break;
                case 0:
                    status.setImageResource(R.drawable.ic_cash_black);
                    statusText.setText("platit");
                    data.jobArray[data.lastJobInteraction].status = 1;
                    break;
            }
        });
    }
}