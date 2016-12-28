package com.xiangsheng.dao.impl;

/**
 * Created by wangliang on 2016/12/23.
 */

public class DBVersionController {

    static final int DB_VERSION = 5;

    static final String DATABASE_NAME = "XiangSheng.db";

    //版本一的表结构
    static final String SQL_CREATE_PEOPLE_TABLE =
            "create table if not exists table_people_profile " +
                    "(ID INTEGER  primary key autoincrement, name text, head_url text, detail_url text);";

    //版本二的表结构
    static final String SQL_CREATE_WORKS_NAME_TABLE =
            "create table if not exists table_works_name " +
                    "(ID INTEGER primary key autoincrement, name text, detail_url text, authorID INTEGER);";

    //版本三的表结构
    static final String SQL_CREATE_WORKS_SIZE_TABLE =
            "create table if not exists table_works_size " +
                    "(ID INTEGER, size text, file_layout text,  detail_url text, " +
                    "primary key (ID));";

    //版本四的表结构
    static final String SQL_CREATE_WORKS_TABLE =
            "create table if not exists table_works " +
                    "(ID INTEGER, authorID INTEGER, name text,  download_url text, definition INTEGER,video_url text, size text, " +
                    "primary key (ID));";

    //搜查过的汉字
    static final String SQL_CREATE_TABLE_HISTROY_ZI = "create table if not exists table_histroy_zi (zi text primary key);";
    //收藏的汉字表

    static final String SQL_CREATE_TABLE_COLLENT_ZI = "create table if not exists table_collect_zi (zi text primary key);";

    static final String SQL_CREATE_TABLE_LETTER_SPELLING = "create table if not exists table_letter_spelling (letter text , spelling text, linkUrl text, primary key(letter, spelling));";

    static final String SQL_CREATE_TABLE_SPELLING_ZI = "create table if not exists table_spelling_zi (spelling text, zi text, stroke short,  primary key(spelling, zi));";
    //版本二的表结构
    //版本三的表结构
}
