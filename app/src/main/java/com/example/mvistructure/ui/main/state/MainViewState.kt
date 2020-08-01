package com.example.mvistructure.ui.main.state

import com.example.mvistructure.ui.main.model.BlogPost
import com.example.mvistructure.ui.main.model.User

/**
 * Created by Rahul on 01/08/20.
 */

//Todo the Data List ViewState
class MainViewState (

    var blogPost: List<BlogPost>? = null,
    var user: User? = null
)