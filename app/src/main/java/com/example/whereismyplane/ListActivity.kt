package com.example.whereismyplane

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.view.View
import android.widget.ListView


class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listView: ListView = findViewById(R.id.listView)

        var airports = arrayOf("aa","bb","cc")
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, airports.toList())

        listView.adapter = arrayAdapter
    }
}