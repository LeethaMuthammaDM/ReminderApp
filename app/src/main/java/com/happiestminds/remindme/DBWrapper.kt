package com.happiestminds.remindme

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class DBWrapper(ctx:Context) {

    val helper=DBHelper(ctx)//tables are ready
    val db=helper.writableDatabase
    fun addRemind(reminder:Reminder):Boolean{
        //insert
        val values=ContentValues()
        values.put(DBHelper.CLM_TITLE, reminder.title)
        values.put(DBHelper.CLM_DESC,reminder.description)
        values.put(DBHelper.CLM_DATE,reminder.date)
        values.put(DBHelper.CLM_TIME,reminder.time)

        val rowid= db.insert(DBHelper.TABLE_NAME,null,values)
        if (rowid.toInt()==-1){
            return false
        }
        return true
    }
    fun getAllReminder():Cursor{
        //select query
        val clms= arrayOf(DBHelper.CLM_TITLE ,DBHelper.CLM_DESC,DBHelper.CLM_DATE,DBHelper.CLM_TIME)
        return db.query(DBHelper.TABLE_NAME,clms,null,null,null,null,null)//null is like *
    }
    fun deleteRemind(reminder: Reminder){
        //delete
        db.delete(DBHelper.TABLE_NAME,"${DBHelper.CLM_TITLE}=?", arrayOf(reminder.title))
    }
}

