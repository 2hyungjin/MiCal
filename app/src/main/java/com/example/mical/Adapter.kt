package com.example.mical

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todolist.view.*

class Adapter(
    val onViewClick: (todo: Todo) -> Unit,
    val onViewDelClick:(todo:Todo )->Unit

) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var list = arrayListOf<Todo>()

    fun setData(newList: ArrayList<Todo>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtTodo = view.findViewById<TextView>(R.id.txt_todo_content)
        val txtEndLine = view.findViewById<TextView>(R.id.txt_todo_until)
        val imageFlag = view.findViewById<ImageView>(R.id.img_flag)
        fun bind(todo: Todo) {
            if (todo.isDone) {
                imageFlag.setImageResource(R.drawable.ic_baseline_check_circle_24)
            } else imageFlag.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
            view.setOnClickListener {
                if (Check.chkDel){
                    onViewDelClick.invoke(todo)
                }else onViewClick.invoke(todo)
            }
            txtTodo.text = todo.content
            txtEndLine.text = "${todo.endLineMon}-${todo.endLineDay}"

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.todolist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}