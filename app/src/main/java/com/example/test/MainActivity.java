package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<Int> extends AppCompatActivity {

    private int count;
    private ImageView imageViewTasbih;
    private TextView textViewCount;
    private Button buttonReset;
    private Spinner spinner;
    private  String[] TASBEH = {"33", "99", "1000", "70000"};
    private int countLimit = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewTasbih = findViewById(R.id.imageViewTasbih);
        textViewCount = findViewById(R.id.textViewCount);
        buttonReset = findViewById(R.id.buttonReset);
        spinner = findViewById(R.id.spinnerCounter);

        count = 0;

        imageViewTasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textViewCount.setText(Integer.toString(count));
                count++;
                if (count==countLimit) count = 0;
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                textViewCount.setText("0");
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, TASBEH);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                countLimit = Integer.parseInt(value);
                count = 0;
                textViewCount.setText(Integer.toString(count));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }





}