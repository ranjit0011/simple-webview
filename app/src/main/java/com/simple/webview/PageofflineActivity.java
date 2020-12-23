package com.simple.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PageofflineActivity extends AppCompatActivity {
    Button btnretry;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageoffline);

        layout=findViewById(R.id.layout);
        btnretry=findViewById(R.id.btnretry);

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageofflineActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (ConnectionManager.checkconnection(getBaseContext())){
            layout.setVisibility(View.INVISIBLE);
        }
        else {
            layout.setVisibility(View.VISIBLE);
        }
    }
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }
}
