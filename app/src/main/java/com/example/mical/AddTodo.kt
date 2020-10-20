package com.example.mical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        calendar.apply {
            this.minDate=System.currentTimeMillis()
        }
        var selectedMon: Int
        var selectedDay: Int
        btn_add_todo.setOnClickListener {
            if (calendar.year==2020){
                intent.putExtra("endLineMon",getMonFromCal())
                intent.putExtra("endLineDay",getDayFromCal())
                intent.putExtra("title", edt_todo.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
            else Toast.makeText(this, "올해 안에서 설정해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    fun getDayFromCal(): Int {
        return calendar.dayOfMonth
    }
    fun getMonFromCal():Int{
        return calendar.month+1
    }

}
