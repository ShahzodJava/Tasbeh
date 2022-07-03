package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewTasbih = findViewById(R.id.imageViewTasbih);
        textViewCount = findViewById(R.id.textViewCount);
        buttonReset = findViewById(R.id.buttonReset);
        spinner = findViewById(R.id.spinnerCounter);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        sizeName = Sizes.SMALL;
        countLimit = sizeName.getSize();

        count = sizeName.getCount();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.glass);

        imageViewTasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                textViewCount.setText(Integer.toString(count));
                sizeName.setCount(count);

                if (count==countLimit) count = 0;

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
                sizeName.setCount(count);
                textViewCount.setText(""+sizeName.getCount() );
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
                countLimit = Integer.parseInt(value);
                sizeName = Sizes.findBySize(countLimit);
                count = sizeName.getCount();
                textViewCount.setText(Integer.toString(count));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }
}

enum Sizes{
    SMALL(33, 0),
    MEDIUM(99, 0),
    BIG(1000, 0),
    LARGE(70000, 0);


    private int size;
    private int count;
    Sizes(int size, int count) {
        this.size = size;
        this.count = count;
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