package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.SaveSystem;

public class JobDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Button back = findViewById(R.id.backButton4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AppData data = SaveSystem.loadData(this);

        fillBlanks(data);
    }

    private void fillBlanks(AppData data)
    {
        TextView date,details,cost,status;
        date = findViewById(R.id.date);
        String content = "Data: " + data.jobArray[data.lastJobInteraction].day + "/" + data.jobArray[data.lastJobInteraction].month + "/" + data.jobArray[data.lastJobInteraction].year;
        date.setText(content);
        content = data.jobArray[data.lastJobInteraction].jobDetails;
        details = findViewById(R.id.details);
        details.setText(content);
        content = "Cost: " + data.jobArray[data.lastJobInteraction].cost + " RON";
        cost = findViewById(R.id.costDetails);
        cost.setText(content);
        switch(data.jobArray[data.lastJobInteraction].status)
        {
            case 1:
                content = "Status: platit";
                break;
            case 0:
                content = "Status: neplatit";
                break;
        }
        status = findViewById(R.id.statusDetails);
        status.setText(content);
    }
}