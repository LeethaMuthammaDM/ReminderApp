package com.happiestminds.remindme



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get

class ShowReminder : AppCompatActivity() {
   lateinit var listViewReminder: ListView
    lateinit var reminderTextView: TextView
    lateinit var parentView: ConstraintLayout
    lateinit var adapter:ArrayAdapter<Reminder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_reminder)

        listViewReminder = findViewById(R.id.lv)
        reminderTextView = findViewById(R.id.reminderT)
        parentView = findViewById(R.id.parentL)

        adapter = ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, reminderList)

        listViewReminder.adapter = adapter
        setup()

        listViewReminder.setOnItemClickListener { adapterView, view, index, l ->
            val selectedRemind= reminderList[index]
            val dlg= ReminderDailog()

            dlg.isCancelable=false

            val dataBundle= Bundle()
            dataBundle.putString("msg","""Description: ${selectedRemind.description}
                |Date: ${selectedRemind.date}
                |Time:${selectedRemind.time}
            """.trimMargin())
            dataBundle.putInt("idx",index)
            dataBundle.putString("id","${selectedRemind.title}")
            dataBundle.putString("description","${selectedRemind.description}")

            dataBundle.putString("d","${selectedRemind.date}")
            dataBundle.putString("t","${selectedRemind.time}")

            dlg.arguments= dataBundle// passing argument to other class

            dlg.show(supportFragmentManager,null)

            adapter.notifyDataSetChanged()


        }


    }


    /*   listViewReminder.setOnItemClickListener{adapterView, view,index,l->
        val selectedReminder= reminderList[index]
        //reminderTextView.text="Selected Color: $selectedReminder"
        val dlg= ReminderDailog()

        dlg.isCancelable=false

        val dataBundle= Bundle()
        dataBundle.putString("msg","""Description: ${selectedReminder.description}
            |Date:${selectedReminder.date}
            |Time:${selectedReminder.time}
        """.trimMargin())
        dataBundle.putInt("id",index)
        dlg.arguments= dataBundle// passing argument to other class

        dlg.show(supportFragmentManager,null)


    }

} */

    override fun onResume() {

        super.onResume()
        setup()
    }
    private fun setup() {
        val cursor = DBWrapper(this).getAllReminder()
        if (cursor.count > 0) {
            val idxTitle = cursor.getColumnIndexOrThrow(DBHelper.CLM_TITLE)
            val idxDescription = cursor.getColumnIndexOrThrow(DBHelper.CLM_DESC)
            val idxDate = cursor.getColumnIndexOrThrow(DBHelper.CLM_DATE)
            val idxTime=cursor.getColumnIndexOrThrow(DBHelper.CLM_TIME)
            reminderList.clear()
            cursor.moveToFirst()

            do {

                val title = cursor.getString(idxTitle)
                val description = cursor.getString(idxDescription)
                val date = cursor.getString(idxDate)
                val time=cursor.getString(idxTime)
                val reminder= Reminder(title,description,date,time)
                reminderList.add(reminder)
            } while (cursor.moveToNext())

            //sort

            adapter.notifyDataSetChanged()

            Log.d(
                "StudentListActivity",
                "List: ${reminderList}"
            )
            Toast.makeText(this, "Found: ${reminderList.count()}", Toast.LENGTH_LONG).show()
        }
    }
}
