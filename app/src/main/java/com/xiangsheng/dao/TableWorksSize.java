package com.xiangsheng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.xiangsheng.dao.impl.XiangShengDatabaseUtils;
import com.xiangsheng.dao.model.WorksSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 2016/12/26.
 */

public class TableWorksSize {

    private Context context;

    public TableWorksSize(Context context) {
        this.context = context;
    }

    public void insertData(WorksSize worksName) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", worksName.getID());
        contentValues.put("size", worksName.getSize());
        contentValues.put("file_layout", worksName.getFileLayout());
        contentValues.put("detail_url", worksName.getDetailUrl());
        database.insert("table_works_size", null, contentValues);
    }

    public void batchInsertData(List<WorksSize> listRadicals) {
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        String sql = "insert into table_works_size (ID, size, file_layout, detail_url) values(?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (WorksSize worksName : listRadicals) {
            Log.d("table_works_size", worksName.getID() + " " + worksName.getDetailUrl());
            statement.bindLong(1, worksName.getID());
            statement.bindString(2, worksName.getSize());
            statement.bindString(3, worksName.getFileLayout());
            statement.bindString(4, worksName.getDetailUrl());
            statement.executeInsert();
        }
        statement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public WorksSize queryDataByID(int id) {
        WorksSize worksSize = null;
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works_size", null, "ID = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            worksSize = new WorksSize();
            worksSize.setID(cursor.getInt(0));
            worksSize.setSize(cursor.getString(1));
            worksSize.setFileLayout(cursor.getString(2));
            worksSize.setDetailUrl(cursor.getString(3));
        }
        return worksSize;
    }

    public List<WorksSize> queryAllData() {
        List<WorksSize> radicalsList = new ArrayList<>();
        SQLiteDatabase database = XiangShengDatabaseUtils.getInstance(context).openDatabase();
        Cursor cursor = database.query("table_works_size", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                WorksSize worksSize = new WorksSize();
                worksSize.setID(cursor.getInt(0));
                worksSize.setSize(cursor.getString(1));
                worksSize.setFileLayout(cursor.getString(2));
                worksSize.setDetailUrl(cursor.getString(3));
                radicalsList.add(worksSize);
                cursor.moveToNext();
            }
        }
        return radicalsList;
    }

}
