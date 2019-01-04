package com.example.android.helloworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    ImageView androidLayout, tuxLayout;
    Switch sw;
    String[] items = new String[]{"-", "Good morning", "Good afternoon", "God night"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropdown = findViewById(R.id.planets_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        switchSettings();

        longClickBugdroid();
        longClickTux();
    }

    private void longClickBugdroid() {
        androidLayout = findViewById(R.id.imageAndroid);
        androidLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "Toque no Bugdroid para mais informações", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
    private void longClickTux() {
        tuxLayout = findViewById(R.id.imageTux);
        tuxLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Toque no Tux para mais informações", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void switchSettings(){
        androidLayout = findViewById(R.id.imageAndroid);
        tuxLayout = findViewById(R.id.imageTux);
        sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw.isChecked()){
                    tuxLayout.setVisibility(View.INVISIBLE);
                    androidLayout.setVisibility(View.VISIBLE);
                } else {
                    androidLayout.setVisibility(View.INVISIBLE);
                    tuxLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void openLinux(View view){
        Intent intent = new Intent(this, LinuxInfo.class);
        startActivity(intent);
    }

    public void openAndroid(View view){
        Intent intent = new Intent(this, AndroidInfo.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_original, menu);
        return true;
    }

    private void dialog_close() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sair")
                .setMessage("Deseja realmente sair?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.close_item:
                dialog_close();
                break;
            case R.id.item1:
                Toast.makeText(this, "clicked on item1", Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "clicked on item2", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
