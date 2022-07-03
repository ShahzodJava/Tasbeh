package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
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
    private int countLimit;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Sizes sizeName;
    private int time;
    private TextView textViewTime;
    private ImageView settingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewTasbih = findViewById(R.id.imageViewTasbih);
        textViewCount = findViewById(R.id.textViewCount);
        buttonReset = findViewById(R.id.buttonReset);
        spinner = findViewById(R.id.spinnerCounter);

        textViewTime = findViewById(R.id.textViewTime);


        sizeName = Sizes.TINY;
        countLimit = sizeName.getSize();

        count = sizeName.getCount();
        time = sizeName.getTime();

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.glass);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        imageViewTasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;
                if (count>countLimit) {

                    count = 0;
                    time++;
                    sizeName.setTime(time);
                    textViewTime.setText("x"+sizeName.getTime());


                }

                textViewCount.setText(Integer.toString(count));
                sizeName.setCount(count);

                try {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                } catch (Exception e1) {
                    // null first time round
                }

                try {
                    mediaPlayer.reset();
                } catch (Exception e1) {
                    // null first time round
                }

                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.glass);

                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    // rules are rules
                }
                try {
                    mediaPlayer.start();
                } catch (IllegalStateException e) {
                    //everyone else is doing it
                }
                vibrator.vibrate(100);

            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                time = 0;

                sizeName.setCount(count);
                sizeName.setTime(time);

                textViewCount.setText(""+sizeName.getCount());
                textViewTime.setText("x"+sizeName.getTime());
            }
        });
//
        ArrayAdapter<Sizes> adapter = new ArrayAdapter<Sizes>(MainActivity.this, android.R.layout.simple_spinner_item, Sizes.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Log.v("Main Activity","view: " + value);
                System.out.println(value);
                countLimit = Integer.parseInt(value);
                sizeName = Sizes.findBySize(countLimit);

                count = sizeName.getCount();
                time = sizeName.getTime();

                textViewCount.setText(Integer.toString(count));
                textViewTime.setText("x"+time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        settingImageView = findViewById(R.id.settings);
        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }
}

enum Sizes{
    TINY(11,0,0),
    SMALL(33, 0,0),
    MEDIUM(99, 0,0),
    BIG(1000, 0,0),
    LARGE(70000, 0,0);


    private int size;
    private int count;
    private int time;

    Sizes(int size, int count, int time) {
        this.size = size;
        this.count = count;
        this.time = time;
    }

    public int getSize() {
        return this.size;
    }

    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static Sizes findBySize(int size){
        for (Sizes value:values()) {
            if (value.size == size) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return ""+this.getSize();
    }
}