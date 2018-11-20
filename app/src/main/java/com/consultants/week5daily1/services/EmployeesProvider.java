package com.consultants.week5daily1.services;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Objects;

public class EmployeesProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.consultants.week5daily1.services.EmployeesProvider";
    //    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final String URL = "content://" + PROVIDER_NAME + "/employees";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String LAST = "last";
    public static final String DAYS = "day";
    public static final String SALARY = "salary";

    private static HashMap<String, String> EMPLOYEES_PROJECTION_MAP;

    static final int EMPLOYEES = 1;
    static final int EMPLOYEE_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "employees", EMPLOYEES);
        uriMatcher.addURI(PROVIDER_NAME, "employees/#", EMPLOYEE_ID);
    }

    private SQLiteDatabase db;
    //    static final String DATABASE_NAME = "College";
    static final String DATABASE_NAME = "Work";
    static final String EMPLOYEES_TABLE_NAME = "Employee";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + EMPLOYEES_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " last TEXT NOT NULL, " +
                    " day TEXT NOT NULL, " +
                    " salary TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowID = db.insert(EMPLOYEES_TABLE_NAME, "", values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EMPLOYEES_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case EMPLOYEES:
                qb.setProjectionMap(EMPLOYEES_PROJECTION_MAP);
                break;

            case EMPLOYEE_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || Objects.equals(sortOrder, "")) {
            sortOrder = NAME;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case EMPLOYEES:
                count = db.delete(EMPLOYEES_TABLE_NAME, selection, selectionArgs);
                break;

            case EMPLOYEE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(EMPLOYEES_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case EMPLOYEES:
                count = db.update(EMPLOYEES_TABLE_NAME, values, selection, selectionArgs);
                break;

            case EMPLOYEE_ID:
                count = db.update(EMPLOYEES_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case EMPLOYEES:
                return "vnd.android.cursor.dir/vnd.example.students";

            case EMPLOYEE_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}



