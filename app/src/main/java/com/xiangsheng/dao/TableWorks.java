package com.xiangsheng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.xiangsheng.dao.impl.XiangShengDatabaseUtils;
import com.xiangsheng.dao.model.Works;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 2016/12/26.
 */

public class TableWorks {
    private Context context;

    public TableWorks(Context context) {
        this.context = context;
    }

    public void insertData(Works works) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", works.getName());
        contentValues.put("authorID", works.getAuthorID());
        contentValues.put("name", works.getName());
        contentValues.put("download_url", works.getDownloadUrl());
        contentValues.put("definition", works.getDefinition());
        contentValues.put("video_url", works.getVideoUrl());
        contentValues.put("size", works.getSize());
        long result = database.insert("table_works", null, contentValues);
        if (result == 1) {

        }
    }

    public void batchInsertData(List<Works> listRadicals) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        String sql = "insert into table_works (ID, authorID, name, download_url, definition, video_url, size) values(?, ?, ?, ?, ?,?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (Works worksName : listRadicals) {
            Log.d("table_works", worksName.getName() + " " + worksName.getName());
            statement.bindLong(1, worksName.getID());
            statement.bindString(2, worksName.getAuthorID());
            statement.bindString(3, worksName.getName());
            statement.bindString(4, worksName.getDownloadUrl());
            statement.bindLong(5, worksName.getDefinition());
            statement.bindString(6, worksName.getVideoUrl());
            statement.bindString(7, worksName.getSize());
            statement.executeInsert();
        }
        statement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public Works queryDatasByID(int ID) {
        Works radicals = null;
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works", null, "ID = ?", new String[]{String.valueOf(ID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            radicals = new Works();
            radicals.setID(cursor.getInt(0));
            radicals.setAuthorID(cursor.getString(1));
            radicals.setName(cursor.getString(2));
            radicals.setDownloadUrl(cursor.getString(3));
            radicals.setDefinition(cursor.getInt(4));
            radicals.setVideoUrl(cursor.getString(5));
            radicals.setSize(cursor.getString(5));
        }
        return radicals;
    }

    public List<Works> queryDatasByAuthorID(int authorID) {
        List<Works> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works", null, "authorID = ?", new String[]{String.valueOf(authorID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Works radicals = new Works();
                radicals.setID(cursor.getInt(0));
                radicals.setAuthorID(cursor.getString(1));
                radicals.setName(cursor.getString(2));
                radicals.setDownloadUrl(cursor.getString(3));
                radicals.setDefinition(cursor.getInt(4));
                radicals.setVideoUrl(cursor.getString(5));
                radicals.setSize(cursor.getString(6));
                radicalsList.add(radicals);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }

    public List<Works> queryAllData() {
        List<Works> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Works radicals = new Works();
                radicals.setID(cursor.getInt(0));
                radicals.setAuthorID(cursor.getString(1));
                radicals.setName(cursor.getString(2));
                radicals.setDownloadUrl(cursor.getString(3));
                radicals.setDefinition(cursor.getInt(4));
                radicals.setVideoUrl(cursor.getString(5));
                radicals.setSize(cursor.getString(6));
                radicalsList.add(radicals);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }
}
