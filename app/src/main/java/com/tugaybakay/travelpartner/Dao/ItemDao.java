package com.tugaybakay.travelpartner.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tugaybakay.travelpartner.Models.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("select * from items where category=:category order by ID asc")
    List<Item> getAll(String category);

    @Delete
    void delete(Item item);

    @Query("update items set checked=:checked where ID=:id")
    void checkUncheck(int id,boolean checked);

    @Query("delete from items where addedby='system'")
    void deleteAllSystemItems();

    @Query("select * from items where checked = :checked")
    List<Item> getAllChecked(boolean checked);
}
