package com.wgc.labtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    EditText name_textview;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name_textview = findViewById(R.id.name);
        submit_button = findViewById(R.id.login);

//        name_textview.setText("x");

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_textview.getText().toString();
                if(name != null && name.matches("((\\s)*[a-zA-Z]+(\\s)*)+")) {
                    Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    myIntent.putExtra("name", name.trim());
                    startActivity(myIntent);
                }else {
                    Toast.makeText(getBaseContext(),"Name must be letters only", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
