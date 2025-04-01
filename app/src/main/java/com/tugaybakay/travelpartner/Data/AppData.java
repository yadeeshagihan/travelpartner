package com.tugaybakay.travelpartner.Data;

import android.content.Context;
import com.tugaybakay.travelpartner.Constans.MyConstans;
import com.tugaybakay.travelpartner.Database.ItemDb;
import com.tugaybakay.travelpartner.Models.Item;
import java.util.ArrayList;
import java.util.List;

public class AppData {

    ItemDb itemDb;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static int NEW_VERSION = 2;

    public AppData(ItemDb itemDb) {
        this.itemDb = itemDb;
    }

    public AppData(ItemDb itemDb, Context context) {
        this.itemDb = itemDb;
        this.context = context;
    }

    public List<Item> getBasicData(){
        String [] data = {"Badulla","Nuwara-eliya"};
        return prepareItemList(MyConstans.Placetovisit,data);
    }

    public List<Item> getNeedsData(){
        String[] data = {"Backpack","Travel Lock"};
        return prepareItemList(MyConstans.NEEDS_CAMEL_CASE,data);
    }

    public List<Item> getCarSuppliesData(){
        String[] data = {"Pump","Car Jack"};
        return prepareItemList(MyConstans.CAR_SUPPLIES_CAMEL_CASE,data);
    }

    public List<Item> getBeachSupplies(){
        String[] data = {"Sea Glasses","Sea Bed"};
        return prepareItemList(MyConstans.BEACH_SUPPLIES_CAMEL_CASE,data);
    }




    List<Item> prepareItemList(String category,String[] data){
        List<Item> dataList = new ArrayList<>();
        for(int i=0;i<data.length;i++){
            dataList.add(new Item(data[i],category,false));
        }
        return  dataList;
    }


    public List<List<Item>> getAllItemLists(){
        List<List<Item>> lists = new ArrayList<>();
        lists.add(getBasicData());
        lists.add(getBeachSupplies());
        lists.add(getCarSuppliesData());
        lists.add(getNeedsData());
        return lists;
    }

    public void persistAllData(){
        List<List<Item>> lists = getAllItemLists();
        for(List<Item> list : lists){
            for(Item item : list) itemDb.getDao().insert(item);
        }
    }

}
