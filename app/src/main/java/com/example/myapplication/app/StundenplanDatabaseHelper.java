package com.example.myapplication.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StundenplanDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    StundenplanDatabaseHelper(Context context) {
        super(context, context.getResources().getString(R.string.dbname), null, Integer.parseInt(context.getResources().getString(R.string.dbversion)));
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        for (String sql : context.getResources().getStringArray(R.array.dbcreate)) {
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String sql : context.getResources().getStringArray(R.array.dbcreate)) {
            db.execSQL(sql);
        }
    }
}
