package com.example.mvistructure.ui.main

import com.example.mvistructure.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}