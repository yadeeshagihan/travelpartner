package com.tugaybakay.travelpartner;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context c) {
        context = c.getApplicationContext(); // Use application context to avoid memory leaks
    }

    public void open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor select(String query) {
        open(); // Open the database before executing the query
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    // Example method for inserting member details
    public long insertMember(String firstName, String lastName, String username, String email, String phone, String password) {
        long insertedId = -1;
        try {
            open(); // Open the database before insertion

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_FIRST_NAME, firstName);
            values.put(DatabaseHelper.COL_LAST_NAME, lastName);
            values.put(DatabaseHelper.COL_USERNAME, username);
            values.put(DatabaseHelper.COL_EMAIL, email);
            values.put(DatabaseHelper.COL_CONTACT, phone);
            values.put(DatabaseHelper.COL_PASSWORD, password);

            insertedId = database.insert(DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(); // Close the database after insertion
        }
        return insertedId;
    }
}
