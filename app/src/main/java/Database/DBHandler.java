package Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_BOOk);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    private static final String SQL_CREATE_ENTRIES_BOOk =
            "CREATE TABLE " + BOOKUser.User.TABLE_NAME + " (" +
                   BOOKUser.User._ID + " INTEGER PRIMARY KEY," +
                    BOOKUser.User.COLUMN_1 + " TEXT," +
                    BOOKUser.User.COLUMN_2 + " TEXT," +
                    BOOKUser.User.COLUMN_3 + " TEXT," +
                    BOOKUser.User.COLUMN_4 + " TEXT)";




    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BOOKUser.User.TABLE_NAME;




    public long Addinformation(String name,String email,String nic,String mobile){



        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BOOKUser.User.COLUMN_1, name);
        values.put(BOOKUser.User.COLUMN_2, email);
        values.put(BOOKUser.User.COLUMN_3, nic);
        values.put(BOOKUser.User.COLUMN_4, mobile);


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BOOKUser.User.TABLE_NAME, null, values);

        return  newRowId;
    }


    public Boolean UpdateBookDetails(String name,String email,String nic,String mobile) {


        SQLiteDatabase db = getWritableDatabase();

// New value for one column

        ContentValues values = new ContentValues();
        values.put(BOOKUser.User.COLUMN_2, email);
        values.put(BOOKUser.User.COLUMN_3, nic);
        values.put(BOOKUser.User.COLUMN_4, mobile);

// Which row to update, based on the title
        String selection = BOOKUser.User.COLUMN_1 + " LIKE ?";
        String[] selectionArgs = { name };

        int count = db.update(

                BOOKUser.User.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count > 0){
            return true;
        }

        else
        {
            return  false;
        }
    }



    public List readAllInfo(String name){


        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                BOOKUser.User.COLUMN_1,
                BOOKUser.User.COLUMN_2,
                BOOKUser.User.COLUMN_3,
                BOOKUser.User.COLUMN_4,

        };

// Filter results WHERE "title" = 'My Title'
        String selection =BOOKUser.User.COLUMN_1 + "  LIKE ?";
        String[] selectionArgs = { name};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BOOKUser.User.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                BOOKUser.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        List UserInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String User = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_1));
            String Email = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_2));
            String NIC = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_3));
            String MOBILE = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_4));

            UserInfo.add(User);//0
            UserInfo.add(Email);//1
            UserInfo.add(NIC);//2
            UserInfo.add(MOBILE);//3

        }
        cursor.close();
        return UserInfo;

    }

    public List readAllInfosearch(String nic){


        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                BOOKUser.User.COLUMN_1,
                BOOKUser.User.COLUMN_2,
                BOOKUser.User.COLUMN_3,
                BOOKUser.User.COLUMN_4,

        };

// Filter results WHERE "title" = 'My Title'
        String selection =BOOKUser.User.COLUMN_3 + "  LIKE ?";
        String[] selectionArgs = { nic};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BOOKUser.User.COLUMN_2 + " ASC";

        Cursor cursor = db.query(
                BOOKUser.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );



        List UserInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String NIC = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_3));
            String User = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_1));
            String Email = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_2));
            String MOBILE = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_4));

            UserInfo.add(NIC);//0
            UserInfo.add(User);//1
            UserInfo.add(Email);//2
            UserInfo.add(MOBILE);//3

        }
        cursor.close();
        return UserInfo;

    }


    public void deleteinfro(String name){

        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = BOOKUser.User.COLUMN_1 + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {name};
// Issue SQL statement.
        int deletedRows = db.delete(
                BOOKUser.User.TABLE_NAME, selection, selectionArgs);

    }



    public ArrayList readAllInf1 (){

        String username = "avinash";
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                BOOKUser.User.COLUMN_1,
                BOOKUser.User.COLUMN_2,
                BOOKUser.User.COLUMN_3,
                BOOKUser.User.COLUMN_4,

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = BOOKUser.User.COLUMN_1 + " = ?";
        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BOOKUser.User.COLUMN_1 + " ASC";

        Cursor cursor = db.query(
                BOOKUser.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList usernames = new ArrayList<>();
        while(cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(BOOKUser.User.COLUMN_1));
            usernames.add(user);
        }
        cursor.close();
        return usernames;
    }

    public Boolean checkNIC(String nic) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Bookingdetails where nic=?", new String[]{nic});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }


}


