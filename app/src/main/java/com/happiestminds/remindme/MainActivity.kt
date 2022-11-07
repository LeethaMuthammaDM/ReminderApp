package com.happiestminds.remindme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById(R.id.button)

permit()
    }



    private fun permit() {
        if(checkSelfPermission(Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            Log.d("MainActivity","Permission is Not granted")
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR),
                1)

        }else{
            button.isEnabled=true
            Log.d("MainActivity","Location Granted")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty()){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("MainActivity"," Permission granted by user")

                button.isEnabled=true
            }else{

                Log.d("MainActivity","Permission Denied by User")
               Toast.makeText(this,"Please grant calendar permission to add reminder",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun addClick(view: View) {
        val addIntent=Intent(this,AddReminder().javaClass)
        startActivity(addIntent)
    }

    // this is on show click
    fun displayClick(view: View) {

        val showIntent=Intent(this,ShowReminder().javaClass)
        startActivity(showIntent)
    }
}