/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity implements BodyPartFragment.OnClickBodyPart {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    public static final String HEAD_INDEX = "headIndex";
    public static final String BODY_INDEX = "bodyIndex";
    public static final String LEG_INDEX = "legIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        if (savedInstanceState != null) {
            Bundle b = new Bundle();
            b.putInt(HEAD_INDEX, savedInstanceState.getInt(HEAD_INDEX));
            b.putInt(BODY_INDEX, savedInstanceState.getInt(BODY_INDEX));
            b.putInt(LEG_INDEX, savedInstanceState.getInt(LEG_INDEX));
            getIntent().putExtras(b);
        }

            headIndex = getIntent().getIntExtra(HEAD_INDEX, 0);
            bodyIndex = getIntent().getIntExtra(BODY_INDEX, 0);
            legIndex = getIntent().getIntExtra(LEG_INDEX, 0);

            // Create a new head, body and leg instance
            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setImagesId(AndroidImageAssets.getHeads());
            headFragment.setListIndex(headIndex);

            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImagesId(AndroidImageAssets.getBodies());
            bodyFragment.setListIndex(bodyIndex);

            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setImagesId(AndroidImageAssets.getLegs());
            legFragment.setListIndex(legIndex);


            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();
            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit();
            fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    //Nao sei se isso eh uma ma pratica
    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putInt(HEAD_INDEX, headIndex);
        bundle.putInt(BODY_INDEX, bodyIndex);
        bundle.putInt(LEG_INDEX, legIndex);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public void onClickBodyPart(String bodyPart, int index) {
        switch(bodyPart){
            case HEAD_INDEX:
                headIndex = index;
                break;
            case BODY_INDEX:
                bodyIndex = index;
                break;
            case LEG_INDEX:
                legIndex = index;
                break;
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(HEAD_INDEX, headIndex);
        outState.putInt(BODY_INDEX, bodyIndex);
        outState.putInt(LEG_INDEX, legIndex);
    }
}
