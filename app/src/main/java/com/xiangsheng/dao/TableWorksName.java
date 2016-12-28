package com.xiangsheng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.xiangsheng.dao.impl.XiangShengDatabaseUtils;
import com.xiangsheng.dao.model.WorksName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangliang on 2016/12/26.
 */

public class TableWorksName {
    private Context context;

    public TableWorksName(Context context) {
        this.context = context;
    }

    public void insertData(WorksName worksName) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", worksName.getName());
        contentValues.put("detail_url", worksName.getDetailUrl());
        contentValues.put("authorID", worksName.getAuthorID());
        database.insert("table_works_name", null, contentValues);
    }

    public void batchInsertData(List<WorksName> listRadicals) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        String sql = "insert into table_works_name (name, detail_url, authorID) values(?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (WorksName worksName : listRadicals) {
            Log.d("table_works_name", worksName.getName() + " " + worksName.getName());
            statement.bindString(1, worksName.getName());
            statement.bindString(2, worksName.getDetailUrl());
            statement.bindLong(3, worksName.getAuthorID());
            statement.executeInsert();
        }
        statement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public List<WorksName> queryDatasByAuthorID(int authorID) {
        List<WorksName> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works_name", null, "authorID = ?", new String[]{String.valueOf(authorID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                WorksName radicals = new WorksName();
                radicals.setID(cursor.getInt(0));
                radicals.setName(cursor.getString(1));
                radicals.setDetailUrl(cursor.getString(2));
                radicals.setAuthorID(cursor.getInt(3));
                radicalsList.add(radicals);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }


    public List<WorksName> queryAllData() {
        List<WorksName> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works_name", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                WorksName radicals = new WorksName();
                radicals.setID(cursor.getInt(0));
                radicals.setName(cursor.getString(1));
                radicals.setDetailUrl(cursor.getString(2));
                radicals.setAuthorID(cursor.getInt(3));
                radicalsList.add(radicals);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }
}
