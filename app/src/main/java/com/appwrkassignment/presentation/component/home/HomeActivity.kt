package com.appwrkassignment.presentation.component.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.appwrkassignment.R
import com.appwrkassignment.base.BaseActivity
import com.appwrkassignment.base.BaseViewModel
import com.appwrkassignment.data.DataItemModel
import com.appwrkassignment.databinding.LayoutActivityHomeBinding
import com.appwrkassignment.managers.UserProfilePrefs
import com.appwrkassignment.presentation.component.home.adapter.HomeItemAdapter
import com.appwrkassignment.presentation.viewmodels.home.HomeViewModel
import com.appwrkassignment.utilities.extensions.showFullScreenDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {


    // View binding for the home activity
    private var binding: LayoutActivityHomeBinding? = null

    // Preferences manager for user profile settings
    @Inject
    lateinit var userProfilePrefs: UserProfilePrefs


    // View model for the main activity
    private var homeViewModel: HomeViewModel? = null

    var homeItemAdapter: HomeItemAdapter? = null

    var selectedFilter: String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = LayoutActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.HomeViewModelFactory(
                userProfilePrefs,
            )
        )[HomeViewModel::class.java]
        setContentView(binding?.root)
        binding?.tvHeading?.text = getString(R.string.def_home_screen)
        observeViewModel()
        setListeners()
    }


    override fun observeViewModel() {
        super.observeViewModel()
        homeViewModel?.itemList?.observe(this@HomeActivity) {
            setHomeAdapter(it)
        }
    }

    private fun setHomeAdapter(it: MutableList<DataItemModel>?) {
        homeItemAdapter = HomeItemAdapter(it) { data, pos ->
            showFullScreenDialog(
                data
            ) { updatedItem ->
                if (updatedItem != null) {
                    homeViewModel?.updateList(updatedItem)
                    homeItemAdapter?.refreshList(homeViewModel?.getFilteredData(selectedFilter))
                }
            }
        }
        binding?.rvItems?.adapter = homeItemAdapter
    }

    override fun rootLayout(): View? {
        return binding?.root
    }

    override fun baseViewModel(): BaseViewModel? {
        return homeViewModel
    }

    override fun <T> onAPIErrorMessage(model: T?) {
        // API error handling
    }

    private fun setListeners() {
        binding?.toggleGroup?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnOne -> {
                        selectedFilter = "All"
                    }

                    R.id.btnTwo -> {
                        selectedFilter = "Pending"
                    }

                    R.id.btnThree -> {
                        selectedFilter = "Completed"
                    }
                }
                homeItemAdapter?.refreshList(homeViewModel?.getFilteredData(selectedFilter))
            }
        }
    }

}