package com.example.dxfactorinterview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dxfactorinterview.model.User
import com.example.dxfactorinterview.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

}