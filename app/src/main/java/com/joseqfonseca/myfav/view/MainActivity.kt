package com.joseqfonseca.myfav.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.model.Product

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //activeFavorites()
    }

    /*private fun activeFavorites() {
        val sp = getPreferences(Context.MODE_PRIVATE)

        if (sp.getStringSet("FAVORITES", mutableSetOf<String>()) == null)
            sp.edit().putStringSet("FAVORITES", mutableSetOf<String>()).apply()

    }*/
}