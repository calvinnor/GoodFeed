package com.mpaani.goodfeed.core.mock

import com.mpaani.goodfeed.core.data.ApiResponse

class MockApiResponse<T> : ApiResponse<T>() {

    override fun onSuccess(response: T?) {
        // NO-OP
    }

    override fun onFailure(throwable: Throwable) {
        // NO-OP
    }
}
