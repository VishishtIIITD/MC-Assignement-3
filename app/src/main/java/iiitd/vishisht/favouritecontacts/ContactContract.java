package iiitd.vishisht.favouritecontacts;

import android.provider.BaseColumns;

/**
 * Created by Vishisht on 02/10/16.
 */
public class ContactContract {
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    private ContactContract(){}

    public static class Contacts implements BaseColumns {
        public static final String TABLE_NAME       = "ContactDetails";
        public static final String COLUMN_NAME_COL1 = "Name";
        public static final String COLUMN_NAME_COL2 = "PhoneNO";
        public static final String COLUMN_NAME_COL3 = "Address";
        public static final String COLUMN_NAME_COL4 = "DOB";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_COL1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL3 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL4 + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
