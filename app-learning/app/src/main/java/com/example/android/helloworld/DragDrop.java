package com.example.android.helloworld;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class DragDrop extends AppCompatActivity {

    public int backgroundColor;
    public int selectedBackgroundColor;
    public View[] views = new View[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão de voltar
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão de voltar

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

        views[0] = findViewById(R.id.layoutHello1);
        views[1] = findViewById(R.id.layoutHello2);
        views[2] = findViewById(R.id.layoutHello3);
        views[3] = findViewById(R.id.layoutHello4);
        views[4] = findViewById(R.id.layoutHello5);
        views[5] = findViewById(R.id.layoutHello6);
        views[6] = findViewById(R.id.layoutHello7);
        views[7] = findViewById(R.id.layoutHello8);
        for (View v : views) {
            v.setOnDragListener(new myOnDragListener());
        }
    }

    private void selectBackground(Spinner dropdown) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String obj = (String) parent.getItemAtPosition(position); // nome do obj selecionado

                switch(position){
                    case 0:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Chocolate));
                        }
                        backgroundColor = getResources().getColor(R.color.Chocolate);
                        selectedBackgroundColor = getResources().getColor(R.color.Peru);
                        break;
                    case 1:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Black));
                        }
                        backgroundColor = getResources().getColor(R.color.Black);
                        selectedBackgroundColor = getResources().getColor(R.color.DimGray);
                        break;
                    case 2:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Blue));
                        }
                        backgroundColor = getResources().getColor(R.color.Blue);
                        selectedBackgroundColor = getResources().getColor(R.color.CornflowerBlue);
                        break;
                    case 3:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Red));
                        }
                        backgroundColor = getResources().getColor(R.color.Red);
                        selectedBackgroundColor = getResources().getColor(R.color.PaleVioletRed);
                        break;
                    case 4:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Green));
                        }
                        backgroundColor = getResources().getColor(R.color.Green);
                        selectedBackgroundColor = getResources().getColor(R.color.LightGreen);
                        break;
                    case 5:
                        for (View v : views){
                            v.setBackgroundColor(getResources().getColor(R.color.Purple));
                        }
                        backgroundColor = getResources().getColor(R.color.Purple);
                        selectedBackgroundColor = getResources().getColor(R.color.DarkViolet);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spinner, menu);

        MenuItem item = menu.findItem(R.id.colors_spinner);
        Spinner dropdown = (Spinner) item.getActionView();
        selectBackground(dropdown);
        return true;
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

    class myOnDragListener implements OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch(action){
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(selectedBackgroundColor);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(backgroundColor);
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
                    v.setBackgroundColor(backgroundColor);
                    break;
            }
            return true;
        }
    }

}
