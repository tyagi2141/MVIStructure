package com.example.mvistructure.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvistructure.repositry.Repositry
import com.example.mvistructure.ui.main.model.BlogPost
import com.example.mvistructure.ui.main.model.User
import com.example.mvistructure.ui.main.state.MainStateEvent
import com.example.mvistructure.ui.main.state.MainViewState
import com.example.mvistructure.util.AbsentLiveData
import com.example.mvistructure.util.DataState

/**
 * Created by Rahul on 01/08/20.
 */
class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewstate: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewstate

    val datastate: LiveData<DataState<MainViewState>> =
        Transformations.switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let { handelStateEvent(stateEvent) }
        }

    private fun handelStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {

            is MainStateEvent.getBlogPostEvent -> {
                return Repositry.getBlogPost()
            }

            is MainStateEvent.getUserEvent -> {
                return Repositry.getUser(stateEvent.userId)
            }

            is MainStateEvent.none -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPost: List<BlogPost>) {
        val update = getCurrentViewStateOrNot()
        update.blogPost = blogPost
        _viewstate.value = update
    }

    fun setuser(user: User) {
        val update = getCurrentViewStateOrNot()
        update.user = user
        _viewstate.value = update

    }

    fun getCurrentViewStateOrNot(): MainViewState {
        val value = viewState.value.let { it } ?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}