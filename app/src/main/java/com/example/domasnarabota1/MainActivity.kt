package com.example.domasnarabota1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var searchViewQuery: SearchView
    private lateinit var editTextTag: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonClearTags: Button
    private lateinit var listViewTags: ListView

    private val taggedSearches = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private val displayedTags = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchViewQuery = findViewById(R.id.searchViewQuery)
        editTextTag = findViewById(R.id.editTextTag)
        buttonSave = findViewById(R.id.buttonSave)
        buttonClearTags = findViewById(R.id.buttonClearTags)
        listViewTags = findViewById(R.id.listViewTags)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayedTags)
        listViewTags.adapter = adapter


        buttonSave.setOnClickListener {
            val tag = editTextTag.text.toString().trim()
            if (tag.isNotEmpty()) {
                taggedSearches.add(tag)
                refreshList()  // Show updated list
                editTextTag.text.clear()
            }
        }


        buttonClearTags.setOnClickListener {
            taggedSearches.clear()
            refreshList()
        }


        searchViewQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        refreshList()
    }

    private fun refreshList() {
        displayedTags.clear()
        displayedTags.addAll(taggedSearches)
        adapter.notifyDataSetChanged()
    }

    private fun filterList(query: String?) {
        displayedTags.clear()
        if (!query.isNullOrEmpty()) {
            displayedTags.addAll(taggedSearches.filter { it.contains(query, ignoreCase = true) })
        } else {
            displayedTags.addAll(taggedSearches)
        }
        adapter.notifyDataSetChanged()
    }
}
