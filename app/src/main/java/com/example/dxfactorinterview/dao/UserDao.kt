package com.example.dxfactorinterview.dao

import androidx.room.*
import com.example.dxfactorinterview.model.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user : User)

    @Update
    fun updateUser(user : User)

    @Delete
    fun deleteUser(user : User)

    @Query("select * from User")
    fun getUsers() : List<User>

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun isDataExist(email: String,password : String): User?

}