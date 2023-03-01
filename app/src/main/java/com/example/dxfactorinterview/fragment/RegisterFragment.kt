package com.example.dxfactorinterview.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dxfactorinterview.dao.UserDao
import com.example.dxfactorinterview.database.DatabaseHelper
import com.example.dxfactorinterview.databinding.FragmentRegisterBinding
import com.example.dxfactorinterview.model.User
import java.io.ByteArrayOutputStream
import java.io.InputStream


class RegisterFragment : Fragment() {

    lateinit var binding : FragmentRegisterBinding
    lateinit var dao : UserDao
    lateinit var picLauncher : ActivityResultLauncher<String>
    var uri : Uri? = null
    lateinit var byteArray : ByteArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        picLauncher = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
               uri = it
                if(uri!=null){
                    binding.ivProfile.setImageURI(it)
                    byteArray = getBytes(requireActivity().contentResolver.openInputStream(uri!!)!!)
                    Log.i("byteArray", byteArray.contentToString())
                }
            })

        dao = DatabaseHelper.createDatabase(requireActivity()).getUserDao()

        binding.btnRegister.setOnClickListener {
            if(checkEmpty()){
                binding.btnRegister.isEnabled = false
                val user = User(
                    binding.edtFirst.text.toString(),
                    binding.edtLast.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(),
                    byteArray
                )
                dao.insertUser(user)
                findNavController().popBackStack()
                Toast.makeText(requireActivity(),"User Added",Toast.LENGTH_SHORT).show()
            }
        }
        binding.ivProfile.setOnClickListener {
            picLauncher.launch("image/*")
        }
    }

    private fun checkEmpty() : Boolean {
        if(binding.edtFirst.text.isNullOrEmpty()){
            binding.tieFirst.error = "Enter First Name"
            return false
        }
        if(binding.edtLast.text.isNullOrEmpty()){
            binding.tieLast.error = "Enter Last Name"
            return false
        }
        if(binding.edtEmail.text.isNullOrEmpty()){
            binding.tieEmail.error = "Enter Email"
            return false
        }
        if(binding.edtPassword.text.isNullOrEmpty()){
            binding.tiePassword.error = "Enter Password"
            return false
        }
        if(binding.edtRepeatPassword.text.isNullOrEmpty()){
            binding.tieRepeatPassword.error = "Enter Password"
            return false
        }
        if(binding.edtPassword.text.toString() != binding.edtRepeatPassword.text.toString()){
            binding.tieRepeatPassword.error = "Password must be same"
            return false
        }
        if(uri==null){
            Toast.makeText(requireActivity(),"Please Choose Profile Photo",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

}