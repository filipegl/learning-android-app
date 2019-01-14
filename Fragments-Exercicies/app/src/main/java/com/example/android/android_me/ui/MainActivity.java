package com.example.android.android_me.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            headIndex = savedInstanceState.getInt(AndroidMeActivity.HEAD_INDEX, 0);
            bodyIndex = savedInstanceState.getInt(AndroidMeActivity.BODY_INDEX, 0);
            legIndex = savedInstanceState.getInt(AndroidMeActivity.LEG_INDEX, 0);
        }
        nextActivity();
    }

    private void nextActivity(){
        Bundle bundle = new Bundle();
        bundle.putInt(AndroidMeActivity.HEAD_INDEX, headIndex);
        bundle.putInt(AndroidMeActivity.BODY_INDEX, bodyIndex);
        bundle.putInt(AndroidMeActivity.LEG_INDEX, legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button bt = (Button) findViewById(R.id.next_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                headIndex = data.getIntExtra(AndroidMeActivity.HEAD_INDEX, 0);
                bodyIndex = data.getIntExtra(AndroidMeActivity.BODY_INDEX, 0);
                legIndex = data.getIntExtra(AndroidMeActivity.LEG_INDEX, 0);
                nextActivity();
                Log.v("SHIT", "headIndex: " + headIndex);
                Log.v("SHIT", "bodyIndex: " + bodyIndex);
                Log.v("SHIT", "legIndex: " + legIndex);
            } else {
                Log.v("debug", "Esse estado provavelmente eh impossivel.");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(AndroidMeActivity.HEAD_INDEX, headIndex);
        outState.putInt(AndroidMeActivity.BODY_INDEX, bodyIndex);
        outState.putInt(AndroidMeActivity.LEG_INDEX, legIndex);
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
