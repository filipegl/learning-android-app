package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class Emojifier {
    static void detectFaces(Context context, Bitmap bitmap){
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);

        int numFaces = faces.size();
        if (numFaces == 0){
            Toast.makeText(context, "Nenhuma face detectada", Toast.LENGTH_SHORT).show();
        }
        Log.d("FACES", "Number of faces = " + numFaces);

        detector.release();
    }
}
