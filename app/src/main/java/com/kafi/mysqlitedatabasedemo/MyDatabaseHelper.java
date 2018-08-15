package com.kafi.mysqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "student.db";
    private static final String tableName = "student_details";
    private static final String id = "id";
    private static final String name = "name";
    private static final String age = "age";
    private static final String gender = "gender";
    private static final int versionNumber = 2;

    private Context context;

    private static final String create_table = "Create table "+tableName+" ("+id+" integer primary key autoincrement, "+name+" varchar(255),"+age+" integer,"+gender+" varchar(255));";
    private static final String drop_table = "DROP TABLE IF EXISTS " + tableName;
    private static final String select_all = "Select * from " + tableName;

    public MyDatabaseHelper(Context context) {
        super(context, databaseName, null, versionNumber);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Toast.makeText(context, "On create is called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(create_table);
        }catch (Exception e){

            Toast.makeText(context, "Exception"+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            Toast.makeText(context, "On upgrade is called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(drop_table);
            onCreate(sqLiteDatabase);
        }catch (Exception e){

            Toast.makeText(context, "Exception"+e, Toast.LENGTH_SHORT).show();
        }

    }

    public long insertData(String n,String a,String g){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(name,n);
        contentValues.put(age,a);
        contentValues.put(gender,g);

        long rowId = sqLiteDatabase.insert(tableName,null,contentValues);
        return rowId;

    }

    public Cursor displayAllData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(select_all,null);

        return cursor;

    }


    public boolean updateData(String i,String n,String a,String g){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(id,i);
        contentValues.put(name,n);
        contentValues.put(age,a);
        contentValues.put(gender,g);

        sqLiteDatabase.update(tableName,contentValues,id+"= ?",new String[]{i});

        return true;

    }

    public int deleteData(String i){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return  sqLiteDatabase.delete(tableName,id+"=?",new String[]{i});
    }
}
