package iiitd.vishisht.favouritecontacts;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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



}
