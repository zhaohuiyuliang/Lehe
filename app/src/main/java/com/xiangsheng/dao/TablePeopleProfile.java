package com.xiangsheng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.xiangsheng.dao.impl.XiangShengDatabaseUtils;
import com.xiangsheng.dao.model.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 2016/12/23.
 */

public class TablePeopleProfile {

    private Context context;

    public TablePeopleProfile(Context context) {
        this.context = context;
    }

    public void insertData(People useZi) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", useZi.getName());
        contentValues.put("head_url", useZi.getHeadUrl());
        contentValues.put("detail_url", useZi.getDetailUrl());
        database.insert("table_people_profile", null, contentValues);
    }

    public void batchInsertData(List<People> listRadicals) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        String sql = "insert into table_people_profile (name, head_url, detail_url) values(?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (People people : listRadicals) {
            Log.d("table_people_profile", people.getName() + " " + people.getHeadUrl());
            statement.bindString(1, people.getName());
            statement.bindString(2, people.getHeadUrl());
            statement.bindString(3, people.getDetailUrl());
            statement.executeInsert();
        }
        statement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public People queryDatasByAuthorID(int authorID) {
        People people = null ;
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_people_profile", null, "ID = ?", new String[]{String.valueOf(authorID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            people = new People();
            people.setID(cursor.getInt(0));
            people.setName(cursor.getString(1));
            people.setHeadUrl(cursor.getString(2));
            people.setDetailUrl(cursor.getString(3));
        }
        return people;
    }

    public List<People> queryAllData() {
        List<People> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_people_profile", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                People radicals = new People();
                radicals.setID(cursor.getInt(0));
                radicals.setName(cursor.getString(1));
                radicals.setHeadUrl(cursor.getString(2));
                radicals.setDetailUrl(cursor.getString(3));
                radicalsList.add(radicals);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }


}
