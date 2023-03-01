package com.example.dxfactorinterview.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dxfactorinterview.adapter.UserAdapter
import com.example.dxfactorinterview.dao.UserDao
import com.example.dxfactorinterview.database.DatabaseHelper
import com.example.dxfactorinterview.databinding.ActivityHomeBinding
import com.example.dxfactorinterview.fragment.UpdateUserDialog
import com.example.dxfactorinterview.listener.OnItemClickListener
import com.example.dxfactorinterview.model.User

class HomeActivity : AppCompatActivity(), OnItemClickListener {
    lateinit var binding : ActivityHomeBinding
    var list = ArrayList<User>()
    lateinit var dao : UserDao
    lateinit var userAdapter: UserAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.hide()

        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        dao = DatabaseHelper.createDatabase(this).getUserDao()

        list = dao.getUsers() as ArrayList<User>
        userAdapter = UserAdapter(list,this)
        binding.rvUsers.adapter = userAdapter

        binding.fabLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to logout?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    editor.putBoolean("isLogin",false)
                    editor.commit()
                    startActivity(Intent(this,AuthActivity::class.java))
                    finish()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

                }).create().show()
        }

    }

    override fun onDeleteClick(pos: Int) {
        openDialog(pos)
    }

    private fun openDialog(pos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Do you really want to delete?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                dao.deleteUser(list[pos])
                list = dao.getUsers() as ArrayList<User>
                userAdapter = UserAdapter(list,this)
                binding.rvUsers.adapter = userAdapter
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

            }).create().show()
    }

    override fun onUpdateClick(pos: Int) {
        val dialogFragment = UpdateUserDialog(list[pos])
        dialogFragment.show(supportFragmentManager,"MyDialog")
    }
}