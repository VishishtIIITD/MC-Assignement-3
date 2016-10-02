package iiitd.vishisht.favouritecontacts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    ArrayList<String> contactDetails = new ArrayList<String>();
    ListView lv;
    ArrayAdapter<String> adapter;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        delete = (Button)findViewById(R.id.button3);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        contactDetails.add(name);
        readDb(name);

        lv = (ListView)findViewById (R.id.listView2);
        lv.setFastScrollEnabled(true);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactDetails);
        lv.setAdapter(adapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDb(name);
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("delete",true);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void readDb(String name){
        ContactHelper dbHelper = new ContactHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String[] projection = {
                ContactContract.Contacts._ID,
                ContactContract.Contacts.COLUMN_NAME_COL1,
                ContactContract.Contacts.COLUMN_NAME_COL2,
                ContactContract.Contacts.COLUMN_NAME_COL3,
                ContactContract.Contacts.COLUMN_NAME_COL4,
        };


        String selection = ContactContract.Contacts.COLUMN_NAME_COL1 + " = ?";
        String[] selectionArgs = { name };


        String sortOrder = ContactContract.Contacts.COLUMN_NAME_COL1 + " DESC";

        Cursor cursor = db.query(ContactContract.Contacts.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        if(cursor.moveToFirst()){
            contactDetails.add(cursor.getString(cursor.getColumnIndex(ContactContract.Contacts.COLUMN_NAME_COL2)));
            contactDetails.add(cursor.getString(cursor.getColumnIndex(ContactContract.Contacts.COLUMN_NAME_COL3)));
            contactDetails.add(cursor.getString(cursor.getColumnIndex(ContactContract.Contacts.COLUMN_NAME_COL4)));
        }

    }

    public void deleteDb(String name){
        ContactHelper dbHelper = new ContactHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = ContactContract.Contacts.COLUMN_NAME_COL1 + " = ?";
        String[] selectionArgs = { name };
        db.delete(ContactContract.Contacts.TABLE_NAME, selection, selectionArgs);
    }
}
