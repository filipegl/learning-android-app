package com.example.android.android_me.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener, BodyPartFragment.OnClickBodyPart {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null) {
            mTwoPane = true;

            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Creating a new head fragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImagesId(AndroidImageAssets.getHeads());
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // New body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImagesId(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                // New leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImagesId(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {

            if (savedInstanceState != null) {
                headIndex = savedInstanceState.getInt(AndroidMeActivity.HEAD_INDEX, 0);
                bodyIndex = savedInstanceState.getInt(AndroidMeActivity.BODY_INDEX, 0);
                legIndex = savedInstanceState.getInt(AndroidMeActivity.LEG_INDEX, 0);
            }
            nextActivity();
        }
    }

    private void nextActivity() {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                headIndex = data.getIntExtra(AndroidMeActivity.HEAD_INDEX, 0);
                bodyIndex = data.getIntExtra(AndroidMeActivity.BODY_INDEX, 0);
                legIndex = data.getIntExtra(AndroidMeActivity.LEG_INDEX, 0);
                nextActivity();
                Log.v("debug", "headIndex: " + headIndex);
                Log.v("debug", "bodyIndex: " + bodyIndex);
                Log.v("debug", "legIndex: " + legIndex);
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
        int listIndex = position % 12;
        if (mTwoPane) {
            BodyPartFragment newFragment = new BodyPartFragment();
            switch (p) {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setImagesId(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImagesId(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImagesId(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            switch (p) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            nextActivity();
        }
    }

    @Override
    public void onClickBodyPart(String part, int index) {

    }
}
