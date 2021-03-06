package com.wassana.wassana_restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pc on 19/10/2559.
 */
public class FoodTABLE {
    //Explicit
    private MySQLiteOpenHelper objMySQLiteOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";

        public FoodTABLE(Context context){
            objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
            writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
            readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();
        }   //Constructor
        public long addNewFood(String strFood,String strSource, String strPrice){
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(FOOD_TABLE, strFood);
            objContentValues.put(COLUMN_SOURCE,strSource);
            objContentValues.put(COLUMN_PRICE,strPrice);
            return readSqLiteDatabase.insert(FOOD_TABLE, null,objContentValues);
        }
}   //Main Class
