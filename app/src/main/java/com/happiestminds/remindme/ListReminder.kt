package com.happiestminds.remindme

import java.util.Date

var reminderList= mutableListOf<Reminder>()
data class Reminder(var title:String,var description:String,var date:String,var time:String){
    override fun toString(): String {
       return "$title\n"

    }
}



