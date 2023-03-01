package com.example.dxfactorinterview.adapter

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dxfactorinterview.databinding.UserRowItemBinding
import com.example.dxfactorinterview.listener.OnItemClickListener
import com.example.dxfactorinterview.model.User


class UserAdapter(val list : ArrayList<User>,val listener : OnItemClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(val binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.binding.obj = user

        val bmp = BitmapFactory.decodeByteArray(user.data, 0, user.data!!.size)
        holder.binding.ivUser.setImageBitmap(bmp)

        holder.binding.ivUpdate.setOnClickListener {
            listener.onUpdateClick(position)
        }
        holder.binding.ivDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }
    }

    override fun getItemCount() = list.size
}