package com.sweet.toolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sweet.toolbox.classes.AppData;
import com.sweet.toolbox.classes.Job;
import com.sweet.toolbox.classes.SaveSystem;

import org.w3c.dom.Text;

import java.io.File;

public class JobMenu extends AppCompatActivity {

    int editToggle1 = 0, deleteToggle1 = 0;

    @Override
    protected void onResume()
    {
        super.onResume();
        editToggle1 = 0;
        deleteToggle1 = 0;
        fillJobMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_menu);

        Button backButton = findViewById(R.id.backToVehicleMenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button addJobButton = findViewById(R.id.addJob);
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddJobActivity();
            }
        });

        Button editJobButton = findViewById(R.id.editJobButton);
        editJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deleteToggle1 == 1)
                    deleteToggle1 = 0;
                if(editToggle1 == 1)
                {
                    editToggle1 = 0;
                    fillJobMenu();
                }
                else
                {
                    editToggle1 = 1;
                    fillJobMenuEdit();
                }
            }
        });

        setNameAndIcon();
        fillJobMenu();

    }

    private void openAddJobActivity()
    {
        Intent intent = new Intent(this,AddJob.class);
        startActivity(intent);
    }

    private void setNameAndIcon()
    {
        AppData data = SaveSystem.loadData(this);

        TextView licenseTitle = findViewById(R.id.vehicleTitle);
        licenseTitle.setText(data.vehicleArray[data.lastVehicleInteraction].licensePlate);

        ImageView vehicleImage = findViewById(R.id.vehicleImage);
        switch(data.vehicleArray[data.lastVehicleInteraction].typeOfVehicle)
        {
            case 1:
                vehicleImage.setImageResource(R.drawable.ic_delorean_black);
                break;
            case 2:
                vehicleImage.setImageResource(R.drawable.ic_minivan_black);
                break;
            case 3:
                vehicleImage.setImageResource(R.drawable.ic_iveco_black);
                break;
            case 4:
                vehicleImage.setImageResource(R.drawable.ic_atego_black);
                break;
            case 5:
                vehicleImage.setImageResource(R.drawable.ic_scania_black);
                break;
            case 6:
                vehicleImage.setImageResource(R.drawable.ic_trailer_black);
                break;
        }

    }

    private void fillJobMenuEdit()
    {
        AppData data = SaveSystem.loadData(this);

        LinearLayout jobLayout = findViewById(R.id.jobMenuLinearLayout);
        jobLayout.removeAllViews();

        for(int i=1;i<=data.numberOfJobs;i++)
        {
            if(data.jobArray[i].vehicleID == data.lastVehicleInteraction)
            {
                ConstraintLayout jobFrame = createJobFrame(data, i);

                ImageView brakeIcon, engineIcon, suspensionIcon, otherIcon;
                brakeIcon = createBrakeIcon(i,data,jobFrame);
                engineIcon = createEngineIcon(i,data,jobFrame);
                suspensionIcon = createSuspensionIcon(i,data,jobFrame);
                otherIcon = createOtherIcon(i,data,jobFrame);

                addConstraintsToBrake(jobFrame, brakeIcon);
                addConstraintsToEngine(engineIcon,brakeIcon,jobFrame);
                addConstraintsToSuspension(suspensionIcon, brakeIcon, jobFrame);
                addConstraintsToOther(otherIcon, suspensionIcon, jobFrame);

                TextView date = createDateText(i,data,jobFrame);
                addConstraintsToDate(engineIcon,date,jobFrame);

                TextView costText = createCostText(i,data,jobFrame);
                addConstraintsToCost(date,costText,jobFrame);

                TextView statusText = createStatusText(i,data,jobFrame);
                addConstraintsToStatus(costText,statusText,jobFrame);

                switch(data.jobArray[i].status)
                {
                    case 1:
                        createTickIcon(i,jobFrame);
                        break;
                    case 0:
                        createPaymentButton(i,jobFrame,data);
                        break;
                }

                createEditButton(i,jobFrame,data);

                jobLayout.addView(jobFrame);
            }

        }

    }

    private void fillJobMenu()
    {
        AppData data = SaveSystem.loadData(this);

        LinearLayout jobLayout = findViewById(R.id.jobMenuLinearLayout);
        jobLayout.removeAllViews();

        for(int i=1;i<=data.numberOfJobs;i++)
        {
            if(data.jobArray[i].vehicleID == data.lastVehicleInteraction)
            {
                ConstraintLayout jobFrame = createJobFrame(data, i);

                ImageView brakeIcon, engineIcon, suspensionIcon, otherIcon;
                brakeIcon = createBrakeIcon(i,data,jobFrame);
                engineIcon = createEngineIcon(i,data,jobFrame);
                suspensionIcon = createSuspensionIcon(i,data,jobFrame);
                otherIcon = createOtherIcon(i,data,jobFrame);

                addConstraintsToBrake(jobFrame, brakeIcon);
                addConstraintsToEngine(engineIcon,brakeIcon,jobFrame);
                addConstraintsToSuspension(suspensionIcon, brakeIcon, jobFrame);
                addConstraintsToOther(otherIcon, suspensionIcon, jobFrame);

                TextView date = createDateText(i,data,jobFrame);
                addConstraintsToDate(engineIcon,date,jobFrame);

                TextView costText = createCostText(i,data,jobFrame);
                addConstraintsToCost(date,costText,jobFrame);

                TextView statusText = createStatusText(i,data,jobFrame);
                addConstraintsToStatus(costText,statusText,jobFrame);

                switch(data.jobArray[i].status)
                {
                    case 1:
                        createTickIcon(i,jobFrame);
                        break;
                    case 0:
                        createPaymentButton(i,jobFrame,data);
                        break;
                }

                jobLayout.addView(jobFrame);
            }

        }

    }

    private void addConstraintsToStatus(TextView costText, TextView statusText, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(statusText.getId(),ConstraintSet.TOP, costText.getId(), ConstraintSet.BOTTOM, 10);
        cs.connect(statusText.getId(),ConstraintSet.START, costText.getId(), ConstraintSet.START, 0);
        cs.applyTo(frame);
    }

    private void addConstraintsToCost(TextView date, TextView costText, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(costText.getId(),ConstraintSet.TOP, date.getId(), ConstraintSet.BOTTOM, 10);
        cs.connect(costText.getId(),ConstraintSet.START, date.getId(), ConstraintSet.START, 0);
        cs.applyTo(frame);
    }

    private void addConstraintsToDate(ImageView engineIcon, TextView date, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(date.getId(),ConstraintSet.TOP, engineIcon.getId(), ConstraintSet.TOP, 13);
        cs.connect(date.getId(),ConstraintSet.START, engineIcon.getId(), ConstraintSet.END, 35);
        cs.applyTo(frame);
    }

    private void addConstraintsToOther(ImageView otherIcon, ImageView suspensionIcon, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(otherIcon.getId(),ConstraintSet.TOP,suspensionIcon.getId(),ConstraintSet.TOP,0);
        cs.connect(otherIcon.getId(),ConstraintSet.START,suspensionIcon.getId(),ConstraintSet.END,15);
        cs.applyTo(frame);
    }

    private void addConstraintsToSuspension(ImageView suspensionIcon, ImageView brakeIcon, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(suspensionIcon.getId(),ConstraintSet.TOP,brakeIcon.getId(),ConstraintSet.BOTTOM,15);
        cs.connect(suspensionIcon.getId(),ConstraintSet.START,brakeIcon.getId(),ConstraintSet.START,0);
        cs.applyTo(frame);
    }

    private void addConstraintsToBrake(ConstraintLayout frame, ImageView brakeIcon)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(brakeIcon.getId(),ConstraintSet.TOP,frame.getId(),ConstraintSet.TOP,15);
        cs.connect(brakeIcon.getId(),ConstraintSet.START,frame.getId(),ConstraintSet.START,15);
        cs.applyTo(frame);
    }

    private void addConstraintsToEngine(ImageView engineIcon, ImageView brakeIcon, ConstraintLayout frame)
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(engineIcon.getId(),ConstraintSet.TOP,brakeIcon.getId(),ConstraintSet.TOP,0);
        cs.connect(engineIcon.getId(),ConstraintSet.START,brakeIcon.getId(),ConstraintSet.END,15);
        cs.applyTo(frame);
    }

    private TextView createDateText(int id, AppData data, ConstraintLayout frame)
    {
        TextView date = new TextView(this);
        frame.addView(date);
        date.setTag("date"+id);
        date.setId(11000+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        date.setTypeface(tf);
        String fullDate = data.jobArray[id].day + " / " + data.jobArray[id].month + " / " + data.jobArray[id].year;
        date.setText(fullDate);
        date.setTextColor(Color.parseColor("#FBFAEC"));
        date.setTextSize(18);
        return date;
    }

    private TextView createCostText(int id, AppData data, ConstraintLayout frame)
    {
        TextView costText = new TextView(this);
        frame.addView(costText);
        costText.setTag("date"+id);
        costText.setId(11300+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        costText.setTypeface(tf);
        String content = "Cost: " + data.jobArray[id].cost + " lei";
        costText.setText(content);
        costText.setTextColor(Color.parseColor("#757575"));
        costText.setTextSize(14);
        return costText;
    }

    private TextView createStatusText(int id, AppData data, ConstraintLayout frame)
    {
        TextView statusText = new TextView(this);
        frame.addView(statusText);
        statusText.setTag("date"+id);
        statusText.setId(11500+id);
        Typeface tf = ResourcesCompat.getFont(this, R.font.exo_font);
        statusText.setTypeface(tf);
        String pay = "";
        switch (data.jobArray[id].status)
        {
            case 1:
                pay = "platit";
                break;
            case 0:
                pay = "neplatit";
                break;
        }
        String content = "Status: " + pay;
        statusText.setText(content);
        statusText.setTextColor(Color.parseColor("#757575"));
        statusText.setTextSize(14);
        return statusText;
    }

    private ImageView createBrakeIcon(int id, AppData data, ConstraintLayout jobFrame)
    {
        ImageView brakeIcon = new ImageView(this);
        jobFrame.addView(brakeIcon);
        brakeIcon.setTag("image"+id);
        brakeIcon.setId(1200+id);
        brakeIcon.getLayoutParams().height = 80;
        brakeIcon.getLayoutParams().width = 80;
        switch(data.jobArray[id].brakeIcon)
        {
            case 0:
                brakeIcon.setBackgroundResource(R.drawable.ic_brake_black);
                break;
            case 1:
                brakeIcon.setBackgroundResource(R.drawable.ic_brake_white);
                break;
        }
        return brakeIcon;
    }

    private ImageView createEngineIcon(int id, AppData data, ConstraintLayout jobFrame)
    {
        ImageView engineIcon = new ImageView(this);
        jobFrame.addView(engineIcon);
        engineIcon.setTag("image"+id);
        engineIcon.setId(2200+id);
        engineIcon.getLayoutParams().height = 80;
        engineIcon.getLayoutParams().width = 80;
        switch(data.jobArray[id].engineIcon)
        {
            case 0:
                engineIcon.setBackgroundResource(R.drawable.ic_engine_black);
                break;
            case 1:
                engineIcon.setBackgroundResource(R.drawable.ic_engine_white);
                break;
        }
        return engineIcon;
    }

    private ImageView createSuspensionIcon(int id, AppData data, ConstraintLayout jobFrame)
    {
        ImageView suspensionIcon = new ImageView(this);
        jobFrame.addView(suspensionIcon);
        suspensionIcon.setTag("image"+id);
        suspensionIcon.setId(3200+id);
        suspensionIcon.getLayoutParams().height = 80;
        suspensionIcon.getLayoutParams().width = 80;
        switch(data.jobArray[id].suspensionIcon)
        {
            case 0:
                suspensionIcon.setBackgroundResource(R.drawable.ic_suspension_black);
                break;
            case 1:
                suspensionIcon.setBackgroundResource(R.drawable.ic_suspension_white);
                break;
        }
        return suspensionIcon;
    }

    private ImageView createOtherIcon(int id, AppData data, ConstraintLayout jobFrame)
    {
        ImageView otherIcon = new ImageView(this);
        jobFrame.addView(otherIcon);
        otherIcon.setTag("image"+id);
        otherIcon.setId(4200+id);
        otherIcon.getLayoutParams().height = 80;
        otherIcon.getLayoutParams().width = 80;
        switch(data.jobArray[id].otherIcon)
        {
            case 0:
                otherIcon.setBackgroundResource(R.drawable.ic_others_black);
                break;
            case 1:
                otherIcon.setBackgroundResource(R.drawable.ic_others_white);
                break;
        }
        return otherIcon;
    }

    private ConstraintLayout createJobFrame(AppData data, int id)
    {
        ConstraintLayout jobFrame = new ConstraintLayout(this);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = 205;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.rightMargin = 15;
        params.leftMargin = 15;
        params.topMargin = 10;
        params.bottomMargin = 10;
        jobFrame.setLayoutParams(params);
        jobFrame.setClickable(true);
        jobFrame.setId(10000+id);
        jobFrame.setBackgroundResource(R.drawable.person_menu_frame_controller);

        jobFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.lastJobInteraction = id;
                SaveSystem.saveData(JobMenu.this,data);
                openJobDetails();
            }
        });

        return jobFrame;
    }

    private void createTickIcon(int id, ConstraintLayout frame)
    {
        ImageView tick = new ImageView(this);
        frame.addView(tick);
        tick.setImageResource(R.drawable.ic_tick);
        tick.setTag("tick" + id);
        tick.setId(12200 + id);
        tick.getLayoutParams().height = 50;
        tick.getLayoutParams().width = 50;

        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(tick.getId(),ConstraintSet.TOP, frame.getId(), ConstraintSet.TOP, 0);
        cs.connect(tick.getId(),ConstraintSet.BOTTOM, frame.getId(), ConstraintSet.BOTTOM, 0);
        cs.connect(tick.getId(),ConstraintSet.END, frame.getId(), ConstraintSet.END, 50);
        cs.applyTo(frame);
    }

    private void createPaymentButton(int id, ConstraintLayout frame, AppData data) {
        ImageView payButton = new ImageView(this);
        frame.addView(payButton);
        payButton.setImageResource(R.drawable.ic_cash);
        payButton.setTag("payButton" + id);
        payButton.setId(12200 + id);
        payButton.getLayoutParams().height = 110;
        payButton.getLayoutParams().width = 110;
        payButton.setClickable(true);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(payButton.getId(), ConstraintSet.TOP, frame.getId(), ConstraintSet.TOP, 0);
        cs.connect(payButton.getId(), ConstraintSet.BOTTOM, frame.getId(), ConstraintSet.BOTTOM, 0);
        cs.connect(payButton.getId(), ConstraintSet.END, frame.getId(), ConstraintSet.END, 25);
        cs.applyTo(frame);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.jobArray[id].status = 1;
                frame.removeView(payButton);
                createTickIcon(id, frame);
                TextView statusText = findViewById(11500 + id);
                statusText.setText("Status: platit");
                SaveSystem.saveData(JobMenu.this, data);
            }
        });
    }

    private void createEditButton(int id, ConstraintLayout frame, AppData data)
    {
        ImageView editButton = new ImageView(this);
        frame.addView(editButton);
        editButton.setImageResource(R.drawable.ic_edit_button_white);
        editButton.setId(17000+id);
        editButton.getLayoutParams().height = 90;
        editButton.getLayoutParams().width = 90;
        editButton.setClickable(true);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(frame);
        cs.connect(editButton.getId(),ConstraintSet.TOP,12200+id,ConstraintSet.TOP);
        cs.connect(editButton.getId(), ConstraintSet.BOTTOM, 12200+id,ConstraintSet.BOTTOM);
        cs.connect(editButton.getId(), ConstraintSet.END, 12200+id,ConstraintSet.START, 20);
        cs.applyTo(frame);
        editButton.setOnClickListener(view -> {
            data.lastJobInteraction = id;
            SaveSystem.saveData(JobMenu.this,data);
            openEditJob();
        });
    }

    private void openJobDetails()
    {
        Intent intent = new Intent(this, JobDetails.class);
        startActivity(intent);
    }

    private void openEditJob()
    {
        Intent intent = new Intent(this,EditJob.class);
        startActivity(intent);
    }

}