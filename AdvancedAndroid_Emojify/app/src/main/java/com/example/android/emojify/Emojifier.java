package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class Emojifier {



    static Bitmap detectFacesAndOverlayEmoji(Context context, Bitmap bitmap){
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

        Bitmap resultBitmap = bitmap;
        for (int i = 0; i < faces.size(); i++){

            Bitmap emojiBitmap = null;
            switch (whichEmoji(faces.valueAt(i))){
                case SERIO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frown);
                    break;
                case SORRINDO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.smile);
                    break;
                case OLHOS_FECHADOS_SERIO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_frown);
                    break;
                case OLHOS_FECHADOS_SORRINDO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_smile);
                    break;
                case PISCADA_DIREITA:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightwink);
                    break;
                case PISCADA_ESQUERDA:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftwink);
                    break;
                case PISCADA_DIREITA_SERIO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightwinkfrown);
                    break;
                case PISCADA_ESQUERDA_SERIO:
                    emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftwinkfrown);
                    break;
            }
            resultBitmap = addBitmapToFace(resultBitmap, emojiBitmap, faces.valueAt(i));
        }

        detector.release();
        return resultBitmap;
    }

    static Emoji whichEmoji(Face face){

        boolean sorrindo = face.getIsSmilingProbability() >= 0.15;
        boolean olho_esquerdo_aberto = face.getIsLeftEyeOpenProbability() >= 0.2;
        boolean olho_direito_aberto = face.getIsRightEyeOpenProbability() >= 0.2;
        Emoji emoji = Emoji.SORRINDO;

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
        Log.d("EMOJI", "Esquerdo aberto: " +face.getIsLeftEyeOpenProbability() + "Direito aberto: " +face.getIsRightEyeOpenProbability() + "Sorrindo: " + face.getIsSmilingProbability());

        return emoji;
    }
    /**
     * Combines the original picture with the emoji bitmaps
     *
     * @param backgroundBitmap The original picture
     * @param emojiBitmap      The chosen emoji
     * @param face             The detected face
     * @return The final bitmap, including the emojis over the faces
     */
    private static Bitmap addBitmapToFace(Bitmap backgroundBitmap, Bitmap emojiBitmap, Face face) {

        // Initialize the results bitmap to be a mutable copy of the original image
        Bitmap resultBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(),
                backgroundBitmap.getHeight(), backgroundBitmap.getConfig());

        // Scale the emoji so it looks better on the face
        float scaleFactor = 0.9f;

        // Determine the size of the emoji to match the width of the face and preserve aspect ratio
        int newEmojiWidth = (int) (face.getWidth() * scaleFactor);
        int newEmojiHeight = (int) (emojiBitmap.getHeight() *
                newEmojiWidth / emojiBitmap.getWidth() * scaleFactor);


        // Scale the emoji
        emojiBitmap = Bitmap.createScaledBitmap(emojiBitmap, newEmojiWidth, newEmojiHeight, false);

        // Determine the emoji position so it best lines up with the face
        float emojiPositionX =
                (face.getPosition().x + face.getWidth() / 2) - emojiBitmap.getWidth() / 2;
        float emojiPositionY =
                (face.getPosition().y + face.getHeight() / 2) - emojiBitmap.getHeight() / 3;

        // Create the canvas and draw the bitmaps to it
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        canvas.drawBitmap(emojiBitmap, emojiPositionX, emojiPositionY, null);

        return resultBitmap;
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
