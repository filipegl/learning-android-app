package com.example.android.helloworld;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DragDrop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        Toast.makeText(this, "Mantenha presisonado o \"Hello World\"", Toast.LENGTH_LONG).show();

        findViewById(R.id.hello).setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("simple_text", "text");
                View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
                v.startDrag(data, sb, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        findViewById(R.id.layoutHello1).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello2).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello3).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello4).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello5).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello6).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello7).setOnDragListener(new myOnDragListener());
        findViewById(R.id.layoutHello8).setOnDragListener(new myOnDragListener());
    }


    class myOnDragListener implements OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch(action){
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(getResources().getColor(R.color.OrangeRed));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(getResources().getColor(R.color.Chocolate));
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(getResources().getColor(R.color.Chocolate));
                    break;
            }
            return true;
        }
    }

}
