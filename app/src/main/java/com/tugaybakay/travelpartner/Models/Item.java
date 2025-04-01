package com.tugaybakay.travelpartner.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID;

    @ColumnInfo(name = "itemName")
    String itemName;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "addedby")
    String addedby;

    @ColumnInfo(name = "checked")
    boolean checked = false;

    @Ignore
    public Item(String itemName, String category, String addedby, boolean checked) {
        this.itemName = itemName;
        this.category = category;
        this.addedby = addedby;
        this.checked = checked;
    }

    public Item(String itemName, String category, boolean checked) {
        this.itemName = itemName;
        this.category = category;
        this.checked = checked;
        addedby = "system";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
