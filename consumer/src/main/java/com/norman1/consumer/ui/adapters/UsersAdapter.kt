package com.norman1.consumer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norman1.consumer.data.models.User
import com.norman1.consumer.databinding.ItemUserListBinding

class UsersAdapter(private val users: ArrayList<User>, private val clickListener: (User) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {


    fun setData(items: List<User>) {
        users.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ItemUserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.bind(users[position], clickListener)

    override fun getItemCount(): Int = users.size

    inner class UsersViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, click: (User) -> Unit) {
            binding.data = user
            binding.root.transitionName = user.login
            binding.root.setOnClickListener { click(user) }
        }
    }
}