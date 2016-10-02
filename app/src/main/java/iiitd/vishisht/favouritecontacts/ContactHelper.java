package iiitd.vishisht.favouritecontacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iiitd.vishisht.favouritecontacts.ContactContract;

/**
 * Created by Vishisht on 02/10/16.
 */
public class ContactHelper extends SQLiteOpenHelper {

    public ContactHelper(Context context) {
        super(context, ContactContract.DATABASE_NAME, null, ContactContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContactContract.Contacts.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ContactContract.Contacts.DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
