package com.happiestminds.remindme

import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ReminderDailog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dlg: Dialog? = null
        //retrieve bundle
        val message = arguments?.getString("msg")
        val idx=arguments?.getInt("idx")
        val title=arguments?.getString("id")
        val description=arguments?.getString("description")
        val date=arguments?.getString("d")
        val time=arguments?.getString("t")
        val remind=Reminder(title!!,description!!,date!!,time!!)
        //create dialog here


        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
            builder.setMessage(message)
            builder.setPositiveButton("Delete") { dialog, i ->

                //executed when the button is clicked
                DBWrapper(it).deleteRemind(remind)
                reminderList.removeAt(idx!!)
//                val eventID=title!!
//                val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
//                val rows: Int = contentResolver.delete(deleteUri, null, null)
//
             activity?.finish()

            }

            builder.setNegativeButton("Ok") { dialog, i ->

               dialog.cancel()

            }

            dlg = builder.create()
        }
        return dlg!!
    }
}