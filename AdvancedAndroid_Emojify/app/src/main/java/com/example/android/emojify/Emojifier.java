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

        for (int i = 0; i < faces.size(); i++){
            whichEmoji(faces.valueAt(i));
        }

        detector.release();
    }

    static Emoji whichEmoji(Face face){

        boolean sorrindo = false;
        boolean olho_esquerdo_aberto = false;
        boolean olho_direito_aberto = false;
        Emoji emoji = Emoji.SORRINDO;

        if (face.getIsSmilingProbability() >= 0.5) {
            sorrindo = true;
        }
        if (face.getIsRightEyeOpenProbability() >= 0.5){
            olho_direito_aberto = true;
        }
        if (face.getIsLeftEyeOpenProbability() >= 0.5){
            olho_esquerdo_aberto = true;
        }

        if (sorrindo){
            if (olho_direito_aberto && olho_esquerdo_aberto){
                emoji = Emoji.SORRINDO;
            } else {
                if (olho_esquerdo_aberto || olho_direito_aberto){
                    if (olho_direito_aberto){
                        //ESQUERDO FECHADO
                        emoji = Emoji.PISCADA_ESQUERDA;
                    } else {
                        //ESQUERDO ABERTO
                        emoji = Emoji.PISCADA_DIREITA;
                    }
                } else {
                    emoji = Emoji.OLHOS_FECHADOS_SORRINDO;
                }
            }
        } else {
            if (olho_direito_aberto && olho_esquerdo_aberto){
                emoji = Emoji.SERIO;
            } else {
                if (olho_esquerdo_aberto || olho_direito_aberto){
                    if (olho_direito_aberto){
                        //ESQUERDO FECHADO
                        emoji = Emoji.PISCADA_ESQUERDA_SERIO;
                    } else {
                        //ESQUERDO ABERTO
                        emoji = Emoji.PISCADA_DIREITA_SERIO;
                    }
                } else {
                    emoji = Emoji.OLHOS_FECHADOS_SERIO;
                }
            }
        }

        Log.d("EMOJI", "Face" + face.getId() + " - EMOJI: " + emoji.name());

        return emoji;
    }

    public enum Emoji {
        SORRINDO,
        SERIO,
        PISCADA_DIREITA,
        PISCADA_DIREITA_SERIO,
        PISCADA_ESQUERDA,
        PISCADA_ESQUERDA_SERIO,
        OLHOS_FECHADOS_SORRINDO,
        OLHOS_FECHADOS_SERIO;
    }
}
