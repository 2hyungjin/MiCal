package com.example.mical

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TodoViewModel : ViewModel() {

    val todoLiveList = MutableLiveData<ArrayList<Todo>>()
    private var todoList = arrayListOf<Todo>()

    fun doneTodo(todo: Todo) {
        todo.isDone = !todo.isDone
        if (todo.isDone) Check.chkDone++
        else Check.chkDone--
        todoLiveList.value = todoList
    }

    fun addTodo(todo: Todo) {

        todoList.add(todo)
        Check.chkTodo++
        sortTodo()
        todoLiveList.value = todoList
    }

    fun delTodo(todo: Todo) {
        todoList.remove(todo)
        Check.chkTodo--
        if (todo.isDone) Check.chkDone--
        todoLiveList.value = todoList
    }

    fun sortTodo() {
        todoList.sortBy {
            it.endLineMon * 10000 + it.endLineDay
        }
    }


}