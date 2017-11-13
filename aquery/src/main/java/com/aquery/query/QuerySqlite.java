package com.aquery.query;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aquery.utils.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ocittwo on 11/12/17.
 */

public class QuerySqlite {

    private final Context context;
    private DbHelper dbHelper;
    private String table;
    private List<String> field_table;
    private List<String> field_value;

    public QuerySqlite(Context context) {
        this.context = context;
        String db_name = context.getPackageName() + "db";
        dbHelper = new DbHelper(context, db_name);
        field_table = new ArrayList<>();
        field_value = new ArrayList<>();
    }

    public void clearDb() {
        String db_name = context.getPackageName() + "db";
        context.deleteDatabase(db_name);
    }

    public QuerySqlite table(String table) {
        this.table = table;
        return this;
    }

    public void insert(Map<String, Object> data) {

        ContentValues values = new ContentValues();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            field_table.add(entry.getKey());
            values.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StringBuilder queryMakeTable = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table);
        for (int i = 0; i < field_table.size(); i++) {
            String start = "(id INTEGER PRIMARY KEY, ";
            if (i > 0)
                start = ", ";
            String item_field_table = start + field_table.get(i) + " TEXT";
            queryMakeTable.append(item_field_table);
        }
        queryMakeTable.append(")");

        String query = queryMakeTable.toString();
        db.execSQL(query);
        db.insert(table, null, values);
        db.close();

    }

    public List<Map<String, String>> all() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Map<String, String>> data = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table, null);
            if (cursor.moveToFirst()) {
                do {
                    String[] colums = cursor.getColumnNames();
                    Map<String, String> result = new HashMap<>();
                    for (int j = 0; j < colums.length; j++) {
                        String colum = colums[j];
                        String value = cursor.getString(j);
                        result.put(colum, value);
                    }
                    data.add(result);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, String> first() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table, null);
            cursor.moveToFirst();
            String[] colums = cursor.getColumnNames();
            Map<String, String> result = new HashMap<>();
            for (int j = 0; j < colums.length; j++) {
                String colum = colums[j];
                String value = cursor.getString(j);
                result.put(colum, value);
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, String> last() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table, null);
            cursor.moveToLast();
            String[] colums = cursor.getColumnNames();
            Map<String, String> result = new HashMap<>();
            for (int j = 0; j < colums.length; j++) {
                String colum = colums[j];
                String value = cursor.getString(j);
                result.put(colum, value);
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, String> get(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE id=" + id, null);
            cursor.moveToFirst();
            String[] colums = cursor.getColumnNames();
            Map<String, String> result = new HashMap<>();
            for (int j = 0; j < colums.length; j++) {
                String colum = colums[j];
                String value = cursor.getString(j);
                result.put(colum, value);
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean update(int id, Map<String, Object> data) {
        ContentValues values = new ContentValues();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            values.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(table, values, "id LIKE ?" + id, new String[]{String.valueOf(id)});
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
