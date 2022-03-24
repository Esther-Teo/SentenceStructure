package com.example.sentencestructure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.ArrayList
import java.util.Collections.shuffle
import com.squareup.picasso.Picasso;

class SentencePage : AppCompatActivity() {
    private lateinit var myAdapter:ArrayAdapter<String>
    private var urlSentenceWordList = ArrayList<ArrayList<String>>()
    private lateinit var correctAns:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_page)
        //initialise and reference the database
        FirebaseApp.initializeApp(this)
        //get reference
        val fb = FirebaseDatabase.getInstance().reference
        val words = fb.child("keyword")
        val query = words.orderByKey()

        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                populateArrayList(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        //Display sentence and words
        findViewById<ListView>(R.id.wordList).setOnItemClickListener{list,item,index,id ->
            val rightWord = correctAns

            if(rightWord== list.getItemAtPosition(index)){
                Toast.makeText(this,"Hooray! You clicked the right definition!", Toast.LENGTH_SHORT).show()
                displayPage(urlSentenceWordList)
            }else{
                Toast.makeText(this,"Oops! You clicked the wrong definition!", Toast.LENGTH_SHORT).show()
            }

        }
    }
        // rand int to find which word will be the correct answer
        //generate sentence by parsing the fb.child(Keywords).child("$word")
//        val sentence =
//        findViewById<ListView>(R.id.wordList).adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,displayWords)
//Picasso code to pull up image
    //String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
//ImageView ivBasicImage = (ImageView) findViewById(R.id.ivBasicImage);
//Picasso.get().load(imageUri).into(ivBasicImage);


    //add on click event on NEXT button that regenerates sentences
    //2. Create 2nd activity that generates sentences and potential words to answer
    //3. Words to be generated from array/list view with an key value pair -> key value pair to be stored in firebase database
    //4. Populate the text view by randomly shuffling a sentence frm the key value pair
    //5. Populate the list view with values from the key value pairs
    private fun populateArrayList(snapshot: DataSnapshot){
        //write code that pulls from database
        //need to shuffle
        if(!snapshot.hasChildren()){
            Toast.makeText(this,"No qns available", Toast.LENGTH_SHORT)
        }else{
            //Toast.makeText(this,"No qns available", Toast.LENGTH_SHORT)
            val entireList = ArrayList<ArrayList<String>>()
            for(child in snapshot.children){
                val indivList = ArrayList<String>()
                val urlLink = child.child("imageURL").value.toString()
                val sentence = child.child("sentence").value.toString()
                val keyword = child.child("word").value.toString()
                indivList.add(urlLink)
                indivList.add(sentence)
                indivList.add(keyword)
                Log.i("indiv","$indivList")
                entireList.add(indivList)
                //pick random 5, store as [[0,0,0],[0,0,0]]
//                // add to array list and then use adapter

            }
            urlSentenceWordList = entireList
            displayPage(urlSentenceWordList)
        }


    }

    private fun displayPage(urlSentenceWordList: ArrayList<ArrayList<String>>) {
        val displayList = ArrayList<ArrayList<String>>()
        val vocabList = ArrayList<String>()
        urlSentenceWordList.shuffle()
        for (i in 0..4){
            val addingList = urlSentenceWordList[i]
            displayList.add(addingList)
        }
        val displayURL = displayList[0][0]
        val displaySentence = displayList[0][1]
        correctAns = displayList[0][2]
        Picasso.get().load("$displayURL").into(findViewById<ImageView>(R.id.imageView))
        findViewById<TextView>(R.id.textView).text = displaySentence
        displayList.shuffle()
        //ADD THE VOCAB OPTIONS AND VERIFICATION
        for (i in displayList){
            vocabList.add(displayList[displayList.indexOf(i)][2])
        }
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vocabList)
        findViewById<ListView>(R.id.wordList).adapter = myAdapter
    }
}