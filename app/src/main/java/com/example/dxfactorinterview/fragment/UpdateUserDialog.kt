package com.example.dxfactorinterview.fragment

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.dxfactorinterview.R
import com.example.dxfactorinterview.dao.UserDao
import com.example.dxfactorinterview.database.DatabaseHelper
import com.example.dxfactorinterview.databinding.UpdateUserDialogBinding
import com.example.dxfactorinterview.model.User
import java.io.ByteArrayOutputStream
import java.io.InputStream

class UpdateUserDialog(val user: User) : DialogFragment() {

    lateinit var binding : UpdateUserDialogBinding
    lateinit var picLauncher : ActivityResultLauncher<String>
    var uri : Uri? = null
    var byteArray : ByteArray? = null
    lateinit var dao : UserDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UpdateUserDialogBinding.inflate(layoutInflater)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = DatabaseHelper.createDatabase(requireActivity()).getUserDao()

        picLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uri = it
                if(uri!=null){
                    binding.ivProfile.setImageURI(it)
                    byteArray = getBytes(requireActivity().contentResolver.openInputStream(uri!!)!!)
                    Log.i("byteArray", byteArray.contentToString())
                }
            })

        val bmp = BitmapFactory.decodeByteArray(user.data, 0, user.data!!.size)
        binding.ivProfile.setImageBitmap(bmp)

        binding.edtFirst.setText(user.firstName)
        binding.edtLast.setText(user.lastName)
        binding.edtEmail.setText(user.email)
        binding.edtPassword.setText(user.password)
        byteArray = user.data!!

        binding.ivProfile.setOnClickListener {
            picLauncher.launch("image/*")
        }

        binding.btnRegister.setOnClickListener {

            if(checkEmpty()){

                    user.firstName = binding.edtFirst.text.toString()
                    user.lastName = binding.edtLast.text.toString()
                    user.email = binding.edtEmail.text.toString()
                    user.password = binding.edtPassword.text.toString()
                    user.data = byteArray
                dao.updateUser(user)
                dialog?.dismiss()
                activity?.recreate()

            }
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
        if(byteArray==null){
            Toast.makeText(requireActivity(),"Please Choose Profile Photo", Toast.LENGTH_SHORT).show()
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