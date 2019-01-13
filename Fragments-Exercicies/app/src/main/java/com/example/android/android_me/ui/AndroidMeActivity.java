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

    int headIndex;
    int bodyIndex;
    int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        if (savedInstanceState == null) {
            headIndex = getIntent().getIntExtra("headIndex", 0);
            bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            legIndex = getIntent().getIntExtra("legIndex", 0);

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
        bundle.putInt("headIndex", headIndex);
        bundle.putInt("bodyIndex", bodyIndex);
        bundle.putInt("legIndex", legIndex);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public void onClickBodyPart(String bodyPart, int index) {
        switch(bodyPart){
            case "headIndex":
                headIndex = index;
                break;
            case "bodyIndex":
                bodyIndex = index;
                break;
            case "legIndex":
                legIndex = index;
                break;
        }
    }
}
