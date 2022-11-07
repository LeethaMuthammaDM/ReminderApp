package com.happiestminds.remindme


import SubmitDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.CONTENT_URI
import android.provider.CalendarContract.Events
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

class AddReminder : AppCompatActivity(),TimePickerDialog.OnTimeSetListener {
    lateinit var titleEditText: EditText
    lateinit var descriptEditText: EditText

    lateinit var dateButton: Button
    lateinit var timeButton: Button
    lateinit var getTime: String
    lateinit var getdate: String


    var hour:Int=0
    var minute:Int=0
    var yr:Int=0

    var m:Int=0
    var d:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        titleEditText = findViewById(R.id.titleE)
        descriptEditText = findViewById(R.id.describeE)

        dateButton = findViewById(R.id.dateB)
        timeButton = findViewById(R.id.timeB)

        val submitButton = findViewById<Button>(R.id.submitB)


    }

    fun selectDate(view: View) {
        val dlg = DatePickerDialog(this)
        dlg.setOnDateSetListener { dPicker, year, month, day ->


            dateButton.text = "$day-${month + 1}-$year"

            getdate = "$day-${month + 1}-$year"
            yr=year
            m=month
            d=day



        }

        dlg.show()

    }

    fun selectTime(view: View) {
        //Time picker dialog
        val dlg = TimePickerDialog(this, this, 10, 0, true)
        dlg.show()
    }

    override fun onTimeSet(tPicker: TimePicker?, hh: Int, mm: Int) {

        timeButton.text = "$hh:$mm"

        getTime = "$hh-$mm"


        // Instead of the above we can directly change in button
        // timeButton.text="$hour-$minute
        hour=hh
        minute=mm


    }


    fun submitClick(view: View) {

        if (titleEditText.text.isNotEmpty()) {
            val dlg = SubmitDialog()

            dlg.isCancelable = false

            val dataBundle = Bundle()
            dataBundle.putString(
                "msg", """DO you want to submit this?
           |Title: ${titleEditText.text}
           |Description:${descriptEditText.text}
           |Date: ${dateButton.text}
           |Time:${timeButton.text}
        """.trimMargin()
            )
            dlg.arguments = dataBundle// passing argument to other class
            dlg.show(supportFragmentManager, null)
            var title=titleEditText.text

            var reminderObject= Reminder(title.toString(),descriptEditText.text.toString(),getdate,getTime)
            reminderList.add(reminderObject)

            if (DBWrapper(this).addRemind(reminderObject)){
                Toast.makeText(this,"Added",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this," Not Added",Toast.LENGTH_LONG).show()
            }
            

            val dateString="${dateButton.text.toString()} ${timeButton.text.toString()}"
            Log.d("AddReminder", "$dateString")
//            val beginTime = Calendar.getInstance();
//            beginTime.set(yr,m,d,hour,minute)
//            val startMillis = beginTime.getTimeInMillis();
////                val endTime = Calendar.getInstance();
////                endTime.set(2023, Calendar.MAY, 11, 4, 0)
//            val endMillis = beginTime.getTimeInMillis()

//                Log.d("TestDate","$startMilis")
//            val calenderIntent = Intent(Intent.ACTION_INSERT).setType("vnd.android.cursor.item/event")
//                .putExtra(Events.TITLE,titleEditText.text.toString())
//                .putExtra(Events.DESCRIPTION,descriptEditText.text.toString())
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
//                .putExtra(Events.ALL_DAY, false)
//            startActivity(calenderIntent)

            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            val date = format.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            Log.d("Add reminder", "milli: ${cal.timeInMillis}")




            var value =  ContentValues();



            value.put(Events.DTSTART, cal.timeInMillis)
            value.put(Events.DTEND, cal.timeInMillis + 60*1000);
            value.put(Events.TITLE, titleEditText.text.toString());
            value.put(Events.DESCRIPTION, descriptEditText.text.toString());
            value.put(Events.CALENDAR_ID, 1);
            value.put(Events.EVENT_TIMEZONE,"IST")
            value.put(Events.HAS_ALARM, 1)



            var uri1=contentResolver.insert(Events.CONTENT_URI, value);
            Log.d("Add Reminder", "calenderBtnClick:  $uri1")
            val eventID = uri1?.lastPathSegment?.toInt()


            Toast.makeText(this, "Task Scheduled Successfully", Toast.LENGTH_SHORT).show();


            val cr:ContentResolver=contentResolver

            var  values =  ContentValues();
            values.put( CalendarContract.Reminders.EVENT_ID, eventID );
            values.put( CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_DEFAULT );
            values.put( CalendarContract.Reminders.MINUTES, CalendarContract.Reminders.MINUTES_DEFAULT );
            val reminderUri = cr.insert( CalendarContract.Reminders.CONTENT_URI, values );



           Log.d("Add reminder", "reminder uri: $reminderUri")

        }else{
            Toast.makeText(this,"Please enter the reminder",Toast.LENGTH_LONG).show()
        }



        /*   var title = titleEditText.text.toString()
           var description = descriptEditText.text.toString()
           var date = dateButton.text.toString()
           var time = timeButton.text.toString()

           when {
               title.isEmpty() -> titleEditText.error = "Roll number is mandatory"

               description.isEmpty() -> descriptEditText.error = "Add description"


               else -> {
                   //add details
                   val remind = Reminder(title, description, date, time)
                   if (DBWrapper(this).addRemind(remind)) {
                       Toast.makeText(this, " added", Toast.LENGTH_LONG).show()
                   } else {

                       Toast.makeText(this, " Not added", Toast.LENGTH_LONG).show()
                   }
               }

           }*/
    }
        /*
    }*/


        fun cancelClick(view: View) {

            Log.d("cancel", "cancel Clicked")
            //useridEditText.text.clear()

            // passEditText.setText("")
            finish()
        }

    }
