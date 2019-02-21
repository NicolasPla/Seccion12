package com.example.seccion12;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

// CTRL + SHIFT + N -- FIND FILE

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextTitle)
    EditText editTextTitle;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.switchImportance)
    Switch switchImportance;

    @BindString(R.string.switch_notification_on) String switchTextOn;
    @BindString(R.string.switch_notification_off) String switchTextOff;

    private boolean isHighImportance = false;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);    // Right after setContextView
        notificationHandler = new NotificationHandler(this);

        switchImportance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHighImportance = isChecked;
                switchImportance.setText((isChecked) ? switchTextOn : switchTextOff);
            }
        });
    }

    @OnClick(R.id.buttonSend)
    public void click(){

        sendNotification();
    }

    @OnCheckedChanged(R.id.switchImportance)
    public void change(CompoundButton buttonView, boolean isChecked){

    }

    private void sendNotification(){

        String tittle = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!TextUtils.isEmpty(tittle) && !TextUtils.isEmpty(message)){
            Notification.Builder nb = notificationHandler.createNotification(tittle, message, isHighImportance);
            notificationHandler.getManager().notify(1, nb.build());
        }
    }
}
