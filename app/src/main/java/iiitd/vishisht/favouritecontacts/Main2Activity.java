package iiitd.vishisht.favouritecontacts;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button add;
    TextView name;
    TextView phone;
    TextView address;
    TextView dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        add = (Button)findViewById(R.id.button2);
        name = (TextView)findViewById(R.id.editText);
        phone = (TextView)findViewById(R.id.editText2);
        address = (TextView)findViewById(R.id.editText3);
        dob = (TextView)findViewById(R.id.editText4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name",name.getText().toString());
                setResult(RESULT_OK,intent);
                addToDb();
                if (isExternalStorageWritable()){
                    SaveData();
                }
                finish();
            }
        });
    }


    public void addToDb(){
        ContactHelper dbHelper = new ContactHelper(this);
        // Get the database. If it does not exist, this is where it will
        // also be created.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create insert entries
        ContentValues values = new ContentValues();
        values.put(ContactContract.Contacts.COLUMN_NAME_COL1, name.getText().toString());
        values.put(ContactContract.Contacts.COLUMN_NAME_COL2, phone.getText().toString());
        values.put(ContactContract.Contacts.COLUMN_NAME_COL3, address.getText().toString());
        values.put(ContactContract.Contacts.COLUMN_NAME_COL4, dob.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(ContactContract.Contacts.TABLE_NAME, null, values);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            return true;
        }
        return false;
    }

    private void SaveData() {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = name.getText().toString();
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            ArrayList<String> data = new ArrayList<String>();
            data.add(name.getText().toString());
            data.add(phone.getText().toString());
            data.add(address.getText().toString());
            data.add(dob.getText().toString());
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(data);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
