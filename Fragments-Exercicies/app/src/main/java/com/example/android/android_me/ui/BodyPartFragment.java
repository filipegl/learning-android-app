package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {
    public final static String TAG = "BodyPartFragment";
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    public void setImagesId(List<Integer> mImagesId) {
        this.mImagesId = mImagesId;
    }

    public void setListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }

    private List<Integer> mImagesId;
    private int mListIndex;

    public BodyPartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mImagesId = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        if (mImagesId != null){
            imageView.setImageResource(mImagesId.get(mListIndex));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListIndex < mImagesId.size() - 1){
                        mListIndex++;
                    } else {
                        mListIndex=0;
                    }
                    imageView.setImageResource(mImagesId.get(mListIndex));
                }
            });

        } else {
            Log.v(TAG, "Este fragment contém uma lista nula");
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImagesId);
        outState.putInt(LIST_INDEX, mListIndex);
    }
}
