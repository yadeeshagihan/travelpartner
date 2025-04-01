package com.tugaybakay.travelpartner.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tugaybakay.travelpartner.Dao.ItemDao;
import com.tugaybakay.travelpartner.Models.Item;

@Database(entities = {Item.class},version = 1,exportSchema = false)
public abstract class ItemDb extends RoomDatabase {

    public static ItemDb itemDb;
    public static String databaseName = "MyDb";

    public synchronized static ItemDb getDb(Context context){
        if(itemDb == null){
            itemDb = Room.databaseBuilder(context,ItemDb.class,databaseName).allowMainThreadQueries().build();
        }
        return itemDb;

    }

    public abstract ItemDao getDao();
}
