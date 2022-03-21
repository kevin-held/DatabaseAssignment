package com.example.databaseassignment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: DBHelper = DBHelper(this, null)

        addButton.setOnClickListener {
            if (uid.text.isNotEmpty() && firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && rewards.text.isNotEmpty()){
                db.add(uid.text.toString().toInt(), firstName.text.toString(), lastName.text.toString(), rewards.text.toString().toInt())
                uid.text.clear()
                firstName.text.clear()
                lastName.text.clear()
                rewards.text.clear()
            } else {
                Toast.makeText(this, "All Fields Required", Toast.LENGTH_LONG ).show()
            }
        }

        displayButton.setOnClickListener {
            if (lookupUid.text.isNotEmpty()){
                val cursor = db.get(lookupUid.text.toString())
                cursor!!.moveToFirst()
                if (cursor.count != 1){
                    Toast.makeText(this, "Not Found", Toast.LENGTH_LONG ).show()
                } else {
                    val uid = cursor.getInt(cursor.getColumnIndex(DBHelper.UID)).toString()
                    val firstname = cursor.getString(cursor.getColumnIndex(DBHelper.FIRST_NAME))
                    val lastname = cursor.getString(cursor.getColumnIndex(DBHelper.LAST_NAME))
                    val rewards = cursor.getInt(cursor.getColumnIndex(DBHelper.REWARDS)).toString()
                    lookupUid.text.clear()
                    textView.setText(uid + ", " + firstname + ", " + lastname + ", " + rewards)
                }
            }
        }

        deleteButton.setOnClickListener {
            if (lookupUid.text.isNotEmpty()){
                db.delete(lookupUid.text.toString())
                lookupUid.text.clear()
                textView.setText("Display Info Here")
            }
        }
    }
}