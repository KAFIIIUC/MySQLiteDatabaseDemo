package com.kafi.mysqlitedatabasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;

    EditText nameEditTxt,ageEditTxt,genderEditTxt,idEditTxt;
    Button addBtn,displayBtn,updateBtn,deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditTxt = findViewById(R.id.nameEditTxt);
        ageEditTxt = findViewById(R.id.ageEditTxt);
        genderEditTxt = findViewById(R.id.genderEditTxt);
        idEditTxt = findViewById(R.id.idEditTxt);

        addBtn = findViewById(R.id.addBtnId);
        displayBtn = findViewById(R.id.displayAllDataBtn);
        updateBtn = findViewById(R.id.updateBtnId);
        deleteBtn = findViewById(R.id.deleteBtnId);

        myDatabaseHelper = new MyDatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        /*add data*/
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditTxt.getText().toString();
                String age = ageEditTxt.getText().toString();
                String gender = genderEditTxt.getText().toString();

                Long id = myDatabaseHelper.insertData(name,age,gender);

                if (id>0){
                    Toast.makeText(MainActivity.this, "Row "+id+" is successfully inserted ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Row is not successfully inserted ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*display all data*/
        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor resultAll = myDatabaseHelper.displayAllData();

                if (resultAll.getCount() == 0){

                    showData("Error","No Data found");

                }

                StringBuffer stringBuffer = new StringBuffer(); //allData Rakhar jonno

                while (resultAll.moveToNext()){

                    stringBuffer.append("ID : "+resultAll.getString(0)+"\n");
                    stringBuffer.append("Name : "+resultAll.getString(1)+"\n");
                    stringBuffer.append("Age : "+resultAll.getString(2)+"\n");
                    stringBuffer.append("Gender : "+resultAll.getString(3)+"\n\n\n");
                }

                showData("ResultSet",stringBuffer.toString());

            }
        });

        /*update single data*/
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditTxt.getText().toString();
                String age = ageEditTxt.getText().toString();
                String gender = genderEditTxt.getText().toString();
                String id = idEditTxt.getText().toString();

                boolean isUpdated = myDatabaseHelper.updateData(id,name,age,gender);

                if (isUpdated == true){
                    Toast.makeText(MainActivity.this, "Successfully Updated ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unsuccessfully Updated ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*delete single data*/
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = idEditTxt.getText().toString();

                int isDelete = myDatabaseHelper.deleteData(id);

                if (isDelete > 0){
                    Toast.makeText(MainActivity.this, "Successfully Deleted ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unsuccessfully Deleted ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showData(String title,String date){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(date);
        builder.setCancelable(true);
        builder.show();

    }
}
