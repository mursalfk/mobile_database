package com.example.lab14task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.os.Build.ID;

public class MainActivity extends AppCompatActivity {
    EditText etRollNo, etName, etMarks;
    ScrollView sc_View;
    TextView tvRollNumberView, tvNameView, tvMarksView;

    DBHelper helper;
    SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);

        etRollNo = (EditText) findViewById(R.id.etRoll);
        etName = (EditText) findViewById(R.id.etName);
        etMarks = (EditText) findViewById(R.id.etMarks);
        sc_View = (ScrollView) findViewById(R.id.sc_view);
        tvRollNumberView = (TextView) findViewById(R.id.tvRollNoView);
        tvNameView = (TextView) findViewById(R.id.tvNameView);
        tvMarksView = (TextView) findViewById(R.id.tvMarksView);


    }

    public void onAdd(View v){
        String strRollNumber = etRollNo.getText().toString();
        String strName = etName.getText().toString();
        String strMarks = etMarks.getText().toString();

        if(TextUtils.isEmpty(strRollNumber) ||TextUtils.isEmpty(strName) || TextUtils.isEmpty(strMarks) ) {
            etRollNo.setError("Write Roll Number in Integers");
            etName.setError("Write your name correctly");
            etMarks.setError("Enter your marks correctly in Integers");

            return;
        }

        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rollNumber", Integer.parseInt(etRollNo.getText().toString()));
        values.put("name", (etName.getText().toString()));
        values.put("marks", Integer.parseInt(etMarks.getText().toString()));

        db.insert("lab14task", null, values);
        Toast.makeText(this, "Record Added, Successfully", Toast.LENGTH_SHORT).show();

    }

    public void onUpdate(View v){

        if(TextUtils.isEmpty(etRollNo.getText().toString()) || TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etMarks.getText().toString()) ) {
            etRollNo.setError("Write Roll Number in Integers");
            etName.setError("Write your name correctly");
            etMarks.setError("Enter your marks correctly in Integers");

            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("rollNumber", etRollNo.getText().toString());
        cv.put("name", etName.getText().toString());
        cv.put("marks", etMarks.getText().toString());
        String where = "rollNumber = " + etRollNo.getText().toString();
        String whereArgs[] = null;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update("lab14task", cv, where, whereArgs);
        Toast.makeText(this, "Data Updated, successfully", Toast.LENGTH_SHORT).show();
    }

    public void onDelete(View v){
        db= helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rollNumber", etRollNo.getText().toString());
        String where = "rollNumber = " + etRollNo.getText().toString();
        db.delete("lab14task", where, null);

        Toast.makeText(this, "Data Deleted, successfully", Toast.LENGTH_SHORT).show();
    }

    public void onView(View v){
        sc_View.setVisibility(View.VISIBLE);

        db = helper.getReadableDatabase();

        c = db.rawQuery("SELECT rollNumber, name, marks FROM lab14task",null);
         if(c == null){
             Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
         }else if(c.moveToFirst()){
             tvRollNumberView.setText(c.getString(0));
             tvNameView.setText(c.getString(1));
             tvNameView.setText(c.getString(2));
         }
    }

    public void onNext(View v){
        if(c == null){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }else if(c.moveToNext()){
            tvRollNumberView.setText((c.getString(0)));
            tvNameView.setText(c.getString(1));
            tvMarksView.setText(c.getString(2));
        }

    }
    public void onPrevious(View v){
        if(c.moveToPrevious()){
            tvRollNumberView.setText((c.getString(0)));
            tvNameView.setText(c.getString(1));
            tvMarksView.setText(c.getString(2));
        }
    }

    public void onBack(View v){
        sc_View.setVisibility(View.GONE);
    }
}