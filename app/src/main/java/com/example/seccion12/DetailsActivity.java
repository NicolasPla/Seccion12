package com.example.seccion12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewMessage)
    TextView textViewMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    private void setIntentValues(){
        if (getIntent() != null){
            textViewTitle.setText(getIntent().getStringExtra("title"));
            textViewMessage.setText(getIntent().getStringExtra("message"));
        }

    }
}
