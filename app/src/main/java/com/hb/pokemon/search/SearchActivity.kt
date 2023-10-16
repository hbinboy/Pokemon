package com.hb.pokemon.search

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hb.base.BaseActivity
import com.hb.pokemon.R
import com.hb.pokemon.app.MyApplication
import com.hb.pokemon.databinding.ActivitySearchBinding
import com.hb.pokemon.detail.DetailActivity
import com.hb.pokemon.utils.hideKeyboard

/**
 * Activity for searching items with a RecyclerView and implementing swipe-to-refresh functionality.
 * Uses Data Binding to bind UI components.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), OnItemClickListener {

    private var isSlidingUpward = false
    var searchAdapter = SearchAdapter(ArrayList<SearchItem>(), this)

    /**
     * Retrieves the layout resource ID.
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    /**
     * Sets up the action bar with the appropriate title and back navigation.
     */
    override fun setupActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.search_title)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * Initializes the activity. Binds UI components, sets listeners, and observes ViewModel data.
     */
    override fun init() {
        binding.apply {
            root.setOnClickListener {
                hideKeyboard()
            }
            search()
            clear()
            initEditListener()
            swipeRefreshLayout.setOnRefreshListener {
                if (binding.editTextSearch.text.isNullOrBlank()) {
                    Toast.makeText(this@SearchActivity, getString(R.string.search_search_word_empty_tip), Toast.LENGTH_SHORT).show()
                    swipeRefreshLayout.isRefreshing = false
                    return@setOnRefreshListener
                }
                swipeRefreshLayout.isRefreshing = true
                viewModel?.newSearch(getKeyWord())
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@SearchActivity)
                adapter = searchAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        val manager = recyclerView.layoutManager as LinearLayoutManager?
                        if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                            val lastItemPosition = manager?.findLastCompletelyVisibleItemPosition()
                            val itemCount = manager?.itemCount

                            if (itemCount != null) {
                                if (lastItemPosition == itemCount - 1 && isSlidingUpward) {
                                    showCenterLoading()
                                    viewModel?.nextPage(getKeyWord())
                                }
                            }
                        }
                    }
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        isSlidingUpward = dy > 0;
                    }
                })
            }
            // Observes data changes from the ViewModel and updates the UI accordingly.
            viewModel?.getData()?.observe(this@SearchActivity, object : Observer<ArrayList<SearchItem>> {
                override fun onChanged(value: ArrayList<SearchItem>) {
                    searchAdapter.itemList = value
                    searchAdapter.notifyDataSetChanged()
                    binding.swipeRefreshLayout.isRefreshing = false
                    hideCenterLoading()
                }
            })

            // Observes error status from the ViewModel and displays appropriate messages.
            viewModel?.getError()?.observe(this@SearchActivity, object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    hideCenterLoading()
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this@SearchActivity, getString(R.string.error_tips),Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    /**
     * Retrieves the search keyword from the EditText component.
     */
    private fun getKeyWord(): String {
        return "%${binding.editTextSearch.text}%"
    }

    /**
     * Initializes the text change listener for the search EditText component.
     * Enables or disables the clear button and search button based on the input text.
     */
    private fun ActivitySearchBinding.initEditListener() {

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.toString().isNotBlank()) {
                    buttonClear.visibility = View.VISIBLE
                    enableSearchButton()
                } else {
                    buttonClear.visibility = View.GONE
                    disableSearchButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.editTextSearch.text.isNullOrBlank()) {
                    Toast.makeText(this@SearchActivity, getString(R.string.search_search_word_empty_tip), Toast.LENGTH_SHORT).show()
                }
                hideKeyboard()
                showCenterLoading()
                viewModel?.newSearch(getKeyWord())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    /**
     * Initializes the clear button click listener for the search EditText component.
     * Clears the text input when the clear button is clicked.
     */
    private fun ActivitySearchBinding.clear() {
        buttonClear.setOnClickListener {
            editTextSearch.text?.clear()
        }
    }

    /**
     * Initializes the search button click listener.
     * Disables the search button and handles search functionality.
     * Shows loading UI during search operation.
     */
    private fun ActivitySearchBinding.search() {
        buttonSearch.apply {
            disableSearchButton()
            setOnClickListener {
                if (editTextSearch.text.isNullOrBlank()) {
                    return@setOnClickListener
                }
                hideKeyboard()
                showCenterLoading()
                viewModel?.newSearch(getKeyWord())
            }
        }
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    override fun initViewModel(): SearchViewModel {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.app)
        )[SearchViewModel::class.java]
    }

    /**
     * Disables the search button and sets its background to a disabled state.
     */
    fun disableSearchButton() {
        binding.buttonSearch.apply {
            isEnabled = false
            background = getDrawable(R.drawable.ic_disable_search)
        }
    }

    /**
     * Enables the search button and sets its background to an enabled state.
     */
    fun enableSearchButton() {
        binding.buttonSearch.apply {
            isEnabled = true
            background = getDrawable(R.drawable.ic_enable_search)
        }
    }

    /**
     * Handles item click events from the RecyclerView adapter.
     */
    override fun onItemClick(item: SearchItem) {
        var intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(getString(R.string.search_selected_name), item.name)
        startActivity(intent)
    }
}