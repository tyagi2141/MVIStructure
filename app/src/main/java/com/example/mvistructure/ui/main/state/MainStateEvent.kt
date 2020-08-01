package com.example.mvistructure.ui.main.state

/**
 * Created by Rahul on 01/08/20.
 */
sealed class MainStateEvent {

    class getBlogPostEvent() : MainStateEvent()
    class getUserEvent(val userId:String) : MainStateEvent()
    class none() : MainStateEvent()
}