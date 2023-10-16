package com.hb.pokemon.detail

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hb.base.BaseActivity
import com.hb.pokemon.R
import com.hb.pokemon.app.MyApplication
import com.hb.pokemon.databinding.ActivityDetailBinding

/**
 * Activity displaying detailed information about a Pokemon.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-15
 */
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    // Adapter for displaying abilities of the Pokemon
    var abilitiesAdapter = AbilitiesAdapter(ArrayList<String>())

    /**
     * Returns the layout resource ID for this activity.
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    /**
     * Sets up the action bar with the title and back button.
     */
    override fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.detail_title)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * Initializes the UI components and fetches data to display.
     */
    override fun init() {
        showCenterLoading()
        binding.apply {
            // Observes changes in detail data and updates the UI accordingly
            viewModel?.getData()?.observe(this@DetailActivity, object : Observer<DetailData> {
                override fun onChanged(value: DetailData) {
                    binding.name.text = value.name
                    abilitiesAdapter.abilities = value.abilities
                    abilitiesAdapter.notifyDataSetChanged()
                    hideCenterLoading()
                }
            })

            // Observes errors and displays an error message if necessary
            viewModel?.getError()?.observe(this@DetailActivity, object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    hideCenterLoading()
                    Toast.makeText(this@DetailActivity, getString(R.string.error_tips),Toast.LENGTH_SHORT).show()
                }
            })

            // Configures the RecyclerView to display abilities
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = abilitiesAdapter
            }
        }

        // Retrieve the selected Pokemon's name from the intent and initiate a search
        val name = intent.getStringExtra(getString(R.string.search_selected_name))
        viewModel?.search(name!!)
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    override fun initViewModel(): DetailViewModel? {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.app)
        )[DetailViewModel::class.java]
    }
}