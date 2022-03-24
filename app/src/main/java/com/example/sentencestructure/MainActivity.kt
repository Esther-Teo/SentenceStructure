package com.example.sentencestructure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener(){
            openGame()
        }

    }
    //1. Create MainActivity with button to start sentence game
    //Set an onclick action to open intent
    private fun openGame(){
        //create intent and redirect to new activity
        val intent = Intent(this,SentencePage::class.java)
        startActivity(intent)
    }
    //2. Create 2nd activity that generates sentences and potential words to answer
    //3. Words to be generated from array/list view with an key value pair -> key value pair to be stored in firebase database
}