package com.example.dxfactorinterview.database

import android.content.Context
import androidx.room.Room

class DatabaseHelper {

    companion object {
        private const val DATABASE_NAME: String = "userDatabase.db"
        fun createDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().build()
        }
    }
}