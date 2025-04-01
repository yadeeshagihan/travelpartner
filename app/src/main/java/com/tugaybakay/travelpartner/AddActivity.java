package com.tugaybakay.travelpartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.tugaybakay.travelpartner.Adapters.Adapter;
import com.tugaybakay.travelpartner.Constans.MyConstans;
import com.tugaybakay.travelpartner.Data.AppData;
import com.tugaybakay.travelpartner.Database.ItemDb;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ItemDb database;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RecyclerView recyclerView;
    CompassView compassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add);

        recyclerView = findViewById(R.id.recyclerView);
        compassView = findViewById(R.id.compassView);

        setConstants();
        persistAppData();

        database = ItemDb.getDb(this);
        adapter = new Adapter(titles, images);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Button playButton = findViewById(R.id.playbtn);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, playActivity.class);
                startActivity(intent);
            }
        });

        // Example: Set direction to 90 degrees (east)
        compassView.setDirection(90);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setConstants() {
        titles = new ArrayList<>();
        titles.add(MyConstans.Placetovisit);
        titles.add(MyConstans.CLOTHING_CAMEL_CASE);
        titles.add(MyConstans.PERSONAL_CARE_CAMEL_CASE);
        titles.add(MyConstans.BABY_NEEDS_CAMEL_CASE);

        images = new ArrayList<>();
        images.add(R.drawable.p1);
        images.add(R.drawable.p2);
        images.add(R.drawable.p3);
        images.add(R.drawable.p4);
    }

    private void persistAppData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        database = ItemDb.getDb(this);
        AppData appData = new AppData(database);
        int last = sharedPreferences.getInt(AppData.LAST_VERSION, 0);
        if (!sharedPreferences.getBoolean(MyConstans.FIRST_TIME_CAMEL_CASE, false)) {
            appData.persistAllData();
            editor.putBoolean(MyConstans.FIRST_TIME_CAMEL_CASE, true);
            editor.commit();
        } else if (last < AppData.NEW_VERSION) {
            database.getDao().deleteAllSystemItems();
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.commit();
        }
    }
}
