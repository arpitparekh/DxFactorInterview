package com.example.dxfactorinterview.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dxfactorinterview.R
import com.example.dxfactorinterview.dao.UserDao
import com.example.dxfactorinterview.activity.HomeActivity
import com.example.dxfactorinterview.database.DatabaseHelper
import com.example.dxfactorinterview.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding
    lateinit var dao : UserDao
    lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = DatabaseHelper.createDatabase(requireActivity()).getUserDao()
        editor = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit()

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            if(checkEmpty()){
                if(dao.isDataExist(binding.edtEmail.text.toString()
                        ,binding.edtPassword.text.toString())!=null){
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    editor.putBoolean("isLogin",true)
                    editor.commit()
                    Toast.makeText(requireActivity(),"Welcome", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireActivity(),"User not exist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkEmpty(): Boolean {
        if(binding.edtEmail.text.isNullOrEmpty()){
            binding.tieEmail.error = "Enter Email"
            return false
        }
        if(binding.edtPassword.text.isNullOrEmpty()){
            binding.tiePassword.error="Enter Password"
            return false
        }
        return true
    }
}