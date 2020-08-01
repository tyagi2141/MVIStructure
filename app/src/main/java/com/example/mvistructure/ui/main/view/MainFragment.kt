package com.example.mvistructure.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvistructure.R
import com.example.mvistructure.ui.main.DataStateListener
import com.example.mvistructure.ui.main.adapter.BlogPostListAdapter
import com.example.mvistructure.ui.main.model.BlogPost
import com.example.mvistructure.ui.main.state.MainStateEvent
import com.example.mvistructure.ui.main.viewmodel.MainViewModel
import com.example.mvistructure.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by Rahul on 01/08/20.
 */
class MainFragment : Fragment(), BlogPostListAdapter.Interaction {
    lateinit var viewModel: MainViewModel
    lateinit var mainRecyclerAdapter: BlogPostListAdapter
    lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        initRecyclerView()
        subscribeObservers()
      //  viewModel.setStateEvent(MainStateEvent.getBlogPostEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }

    }

    private fun subscribeObservers() {

        viewModel.datastate.observe(viewLifecycleOwner, Observer {
            dataStateHandler.onDataStateChange(it)
            it.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPost?.let {
                        viewModel.setBlogListData(it)
                    }
                }

            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPost?.let {
                mainRecyclerAdapter.submitList(it)
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = BlogPostListAdapter(this@MainFragment)
            adapter = mainRecyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: CLICKED ${position}")
        println("DEBUG: CLICKED ${item}")    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_get_blogs-> triggerGetBlogsEvent()
           // R.id.action_get_user-> triggerGetUserEvent()
        }

        return super.onOptionsItemSelected(item)
    }

    fun triggerGetBlogsEvent(){
        viewModel.setStateEvent(MainStateEvent.getBlogPostEvent())
    }
}