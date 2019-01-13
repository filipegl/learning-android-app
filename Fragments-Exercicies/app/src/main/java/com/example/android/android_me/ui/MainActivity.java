package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex = 0;
    private int bodyIndex = 0;
    private int legIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            headIndex = savedInstanceState.getInt("headIndex", 0);
            bodyIndex = savedInstanceState.getInt("bodyIndex", 0);
            legIndex = savedInstanceState.getInt("legIndex", 0);
        }
        nextActivity();
    }


    private void nextActivity(){
        Bundle bundle = new Bundle();
        bundle.putInt("headIndex", headIndex);
        bundle.putInt("bodyIndex", bodyIndex);
        bundle.putInt("legIndex", legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button bt = (Button) findViewById(R.id.next_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("headIndex", headIndex);
        outState.putInt("bodyIndex", bodyIndex);
        outState.putInt("legIndex", legIndex);
    }

    @Override
    public void onImageSelected(int position) {
        int p = position / 12;
        switch (p){
            case 0:
                headIndex = position % 12;
                break;
            case 1:
                bodyIndex = position % 12;
                break;
            case 2:
                legIndex = position % 12;
                break;
            default:
                break;
        }
        nextActivity();
    }
}
