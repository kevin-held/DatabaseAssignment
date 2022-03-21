package com.example.databaseassignment

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private val context = context

    companion object {
        private val DATABASE_NAME = "SIMPLEDATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "simpletable"
        val UID = "uid"
        val FIRST_NAME = "firstname"
        val LAST_NAME = "lastname"
        val REWARDS = "rewards"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + UID + " INTEGER UNIQUE, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + REWARDS + " INTEGER" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun add(id: Int, first: String, last: String, rewards: Int) {
        val values = ContentValues()
        values.put(UID, id)
        values.put(FIRST_NAME, first)
        values.put(LAST_NAME, last)
        values.put(REWARDS, rewards)
        try {
            val cursor = get(id.toString())
            cursor!!.moveToFirst()
            if (cursor.count != 0){
                delete(id.toString()) // delete to overwrite
            }
            val db = this.writableDatabase
            db.insert(TABLE_NAME, null, values)
            db.close()
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun delete(id: String){
        try{
            val db = this.readableDatabase
            db.delete(TABLE_NAME, UID + " = " + id, null)
            //db.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE " + UID + " = " + id, null)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        }

    }

    fun get(id: String): Cursor? {
        try {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + UID + " = " + id, null)
        } catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        }
        return null
    }
}