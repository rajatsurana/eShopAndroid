package com.rajat.eshop.db;

/**
 * Created by Rajat on 22-10-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rajat.eshop.Pojo.Notification;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DatabaseHandler";

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "notificationDB";

    private static final String TABLE_NOTIFICATIONS= "notifications";
    private static final String KEY_ID="_id";

    private static final String KEY_DESCRIPTION="description";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE "
            + TABLE_NOTIFICATIONS + "(" +  KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DESCRIPTION + " TEXT"
            + ")";
    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);
    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }
    // create new record
    // @param myObj contains details to be added as single row.
    public boolean create(Notification myObj) {
        Log.i("rajat","create Complaint");
        boolean createSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS, null);
        int count=0;
        if (c.getCount()>0) {
            c.moveToLast();

            count = c.getInt(c.getColumnIndex(KEY_ID));
        }
        ContentValues values = new ContentValues();
        values.put(KEY_ID, count+1);

        values.put(KEY_DESCRIPTION, myObj.getMessage());

        createSuccessful = db.insert(TABLE_NOTIFICATIONS, null, values) > 0;
        if(createSuccessful){
            Log.e(TAG, myObj.getMessage() + " created.");
        }
        //}

        return createSuccessful;
    }
    public ArrayList<Notification> readAllNotifications() {

        ArrayList<Notification> recordsList = new ArrayList<Notification>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_NOTIFICATIONS ;


        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String description=cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
                int notificationId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    Notification mv= new Notification(description,notificationId);
                    // add to list
                    recordsList.add(mv);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return the list of records
        return recordsList;
    }





    public Notification readMyNotificationAtPosition(int position) {
        List<Notification> recordsList = new ArrayList<Notification>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_NOTIFICATIONS +" WHERE " + KEY_ID + " = '" + position + "'";


        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);
        Notification mv=null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {


             int notificationId = cursor.getInt(cursor.getColumnIndex(KEY_ID));

            String description=cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));


            mv= new Notification(description,notificationId);//brand,model,plateNumb, Double.valueOf(mileag)
            // add to list
            //recordsList.add(mv);


        }

        cursor.close();
        //db.close();

        // return the list of records
        return mv;
    }

    public void deleteMyNotification(int complaintId) {
        //SELECT MAX( `column` ) FROM `table` ;
        //ALTER TABLE `table` AUTO_INCREMENT = number;

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " +TABLE_NOTIFICATIONS +" WHERE " +KEY_ID +" = '" +complaintId+"'";
        //Cursor cursorDelete = db.rawQuery(sql, null);
        db.execSQL(sql);

    }
    public void deleteAllMyNotifications() {
        //SELECT MAX( `column` ) FROM `table` ;
        //ALTER TABLE `table` AUTO_INCREMENT = number;

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NOTIFICATIONS ;//+ " WHERE " + KEY_MY_VEHICLE_ID + " = '" + cardPos + "'";
        //Cursor cursorDelete = db.rawQuery(sql, null);
        db.execSQL(sql);
    }
}