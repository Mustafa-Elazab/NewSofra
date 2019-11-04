package com.example.mustafa.sofraNew.data.local.room;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    void insert(FoodItemsData... FoodItemsData);

    @Update
    void update(FoodItemsData ... FoodItemsData);

    @Delete
    void delete(FoodItemsData ... FoodItemsData);

    @Query("Delete from FoodItemsData")
    void deleteAllItemToCart();

    @Query("Select * from FoodItemsData")
    List<FoodItemsData> getAllItem();
}
