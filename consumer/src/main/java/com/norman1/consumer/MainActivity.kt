package com.norman1.consumer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.norman1.consumer.databinding.ActivityMainBinding
import com.norman1.consumer.ui.adapters.UsersAdapter
import com.norman1.consumer.ui.fragments.DetailsFragment
import com.norman1.consumer.ui.viewmodels.UsersViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        usersAdapter = UsersAdapter(ArrayList()) { user ->
            val fragment = DetailsFragment.newInstance(user)
            fragment.show(supportFragmentManager, "userDetails")
        }

        activityMainBinding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        usersViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UsersViewModel::class.java)
        activityMainBinding.progress.visibility = View.VISIBLE
        usersViewModel.userLists.observe(this, { users ->
            if (!users.isNullOrEmpty()) {
                activityMainBinding.progress.visibility = View.GONE
                usersAdapter.setData(users)
            } else {
                activityMainBinding.apply {
                    progress.visibility = View.GONE
                    mainError.visibility = View.VISIBLE
                }
            }
        })
    }
}
