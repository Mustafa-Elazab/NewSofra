package com.example.mustafa.sofraNew.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;

@Database(entities = {FoodItemsData.class}, version = 1, exportSchema = false)
public abstract class RoomManger extends RoomDatabase {
    private static RoomManger roomManger;

    public abstract RoomDao roomDao();

    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {

            roomManger = Room.databaseBuilder(context.getApplicationContext(), RoomManger.class,
                    "Sofra_Database").fallbackToDestructiveMigration().build();

        }

        return roomManger;
    }
}
