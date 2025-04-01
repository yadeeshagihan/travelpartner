package com.tugaybakay.travelpartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.tugaybakay.travelpartner.Adapters.CheckListAdapter;
import com.tugaybakay.travelpartner.Constans.MyConstans;
import com.tugaybakay.travelpartner.Database.ItemDb;
import com.tugaybakay.travelpartner.Models.Item;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    List<Item> list = new ArrayList<>();
    ItemDb db;
    CheckListAdapter adapter;
    String header,show;

    RecyclerView recyclerView;
    EditText editText;
    Button button;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        Intent intent = getIntent();
        show = intent.getStringExtra(MyConstans.SHOW_SMALL);
        header = intent.getStringExtra(MyConstans.HEADER_SMALL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(header);

        recyclerView = findViewById(R.id.recyclerViewCheckList);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.buttonCheckListPage);
        layout = findViewById(R.id.linearLayoutFromCheckListPage);
        db = ItemDb.getDb(this);
        if(MyConstans.FALSE_STRING.matches(show)){
            layout.setVisibility(View.GONE);
            list = db.getDao().getAllChecked(true);
        }else{
            list = db.getDao().getAll(header);
        }
        updateRecycler(list);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if(name.matches("")){
                    Toast.makeText(CheckList.this,"Empty!",Toast.LENGTH_SHORT).show();
                }else{
                    addNewItem(name);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Item> searchList = new ArrayList<>();
                for(Item item : list){
                    if(item.getItemName().toLowerCase().startsWith(newText)){
                        searchList.add(item);
                    }
                }
                updateRecycler(searchList);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.mySelection:
                Intent intent = new Intent(this,CheckList.class);
                intent.putExtra(MyConstans.SHOW_SMALL,MyConstans.FALSE_STRING);
                intent.putExtra(MyConstans.HEADER_SMALL,MyConstans.MY_SELECTIONS_CAMEL_CASE);
                startActivityForResult(intent,101);
                break;
            case R.id.exit:
                finishAffinity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 101){
            list = db.getDao().getAll(header);
            updateRecycler(list);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void addNewItem(String name){
        Item item = new Item(name,header,MyConstans.USER_SMALL,false);
        db.getDao().insert(item);
        list.add(item);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
        editText.setText("");

    }

    private void updateRecycler(List<Item> itemList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckListAdapter(itemList,this,db,show);
        recyclerView.setAdapter(adapter);
    }
}