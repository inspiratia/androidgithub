package com.norman1.normanbhaskara.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.databinding.ItemUserListBinding

class UsersAdapter(
    private val users: ArrayList<User>,
    private val clickListener: (String, View) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

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
        fun bind(user: User, click: (String, View) -> Unit) {
            binding.data = user
            binding.root.transitionName = user.login
            binding.root.setOnClickListener { click(user.login, binding.root) }
        }
    }
}