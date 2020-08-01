package com.example.mvistructure.repositry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mvistructure.util.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Rahul on 01/08/20.
 */
abstract class NetworkBoundResource<ResponseObject, ViewStateType> {
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)
        GlobalScope.launch(IO) {
            withContext(Main) {
                val apiResponse = createCall()

                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    handelNetworkCallResponse(response)

                }
            }
        }
    }

    fun handelNetworkCallResponse(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handelApiResponse(response)
            }
            is ApiErrorResponse -> {
                onErrorResponse(response.errorMessage)
            }
            is ApiEmptyResponse -> {
            }
        }
    }

    private fun onErrorResponse(errorMessage: String) {
        result.value = DataState.error(message = errorMessage)
    }

    abstract fun handelApiResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}