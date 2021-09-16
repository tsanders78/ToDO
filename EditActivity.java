package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.todoapp.R.id.txtEdit;

public class EditActivity extends AppCompatActivity {
    EditText txtEdit;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtEdit = findViewById(R.id.txtEdit);
        editButton = findViewById(R.id.btnEdit);

        getSupportActionBar().setTitle("Edit item");

        txtEdit.setText((getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT)));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(); // k is declared as the intent
                k.putExtra(MainActivity.KEY_ITEM_TEXT, txtEdit.getText().toString()); // Stores the text in the key
                k.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION)); // getting data from the store key of each item
                setResult(RESULT_OK, k);
                finish();


            }
        });

    }
}
