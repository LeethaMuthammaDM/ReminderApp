package com.happiestminds.remindme

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,"students.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        //Table creation must be done here

        db?.execSQL(TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //when version mismatch
        //drop, create table, modify schema of existing table
    }

    companion object{
        const val TABLE_NAME="ReminderData"//constant variable name must be capital
        const val CLM_TITLE="title"
        const val CLM_DESC="description"
        const val CLM_DATE="date"
        const val CLM_TIME="time"
        const val TABLE_QUERY="create table $TABLE_NAME($CLM_TITLE text primary key,$CLM_DESC text,$CLM_DATE text,$CLM_TIME text)"
    }
}