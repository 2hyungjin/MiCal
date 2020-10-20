package com.example.mical

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: TodoViewModel by viewModels()
    private val REQUEST_CODE = 101
    private var chkFab: Boolean = false
    lateinit var adapter: Adapter
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = Adapter(onViewClick = {
            viewModel.doneTodo(it)
            Toast.makeText(this, it.id, Toast.LENGTH_SHORT).show()
        },
            onViewDelClick = {
                viewModel.delTodo(it)

            })
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        rv.addItemDecoration(decoration)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        fab_menu.setOnClickListener {
            chkFab = !chkFab
            if (chkFab) {
                showMenu()
            } else hideMenu()
        }


        fab_add.setOnClickListener {
            val intent = Intent(this, AddTodo::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        fab_del.setOnClickListener {
            modeDel()
        }
        viewModel.todoLiveList.observe(this, Observer {
            adapter.setData(it)
            chkTodo()
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val uuid=UUID.randomUUID().toString()
            val title = data?.getStringExtra("title").toString()
            val endLineMon: Int = data?.getIntExtra("endLineMon", 0)!!.toInt()
            val endLineDay: Int = data?.getIntExtra("endLineDay", 0)!!.toInt()
            val todo = Todo(
                id=uuid,
                endLineMon = endLineMon,
                endLineDay = endLineDay,
                content = title,
            )

            viewModel.addTodo(todo)
        }
    }

    fun modeDel() {
        Check.chkDel = !Check.chkDel
        if (Check.chkDel) fab_del.setImageResource(R.drawable.ic_baseline_delete_outline_checked_24)
        else fab_del.setImageResource(R.drawable.ic_baseline_delete_outline_24)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun chkTodo() {
        progressBar.max = Check.chkTodo
        progressBar.setProgress(Check.chkDone, true)
        txt_progress.text = "${Check.chkDone}/${Check.chkTodo}"
    }

    fun showMenu() {
        ObjectAnimator.ofFloat(fab_add, "translationY", -250f).apply { start() }
        ObjectAnimator.ofFloat(fab_del, "translationY", -500f).apply { start() }
    }

    fun hideMenu() {
        ObjectAnimator.ofFloat(fab_add, "translationY", 0f).apply { start() }
        ObjectAnimator.ofFloat(fab_del, "translationY", 0f).apply { start() }
    }

}


