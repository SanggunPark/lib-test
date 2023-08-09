package com.metaverse.world.testlib.api

import com.metaverse.world.testlib.model.remote.LottoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoAPI {
    @GET("/common.do")
    suspend fun getLottoNum(
        @Query("drwNo") drwNum: Int,
        @Query("method") method: String = "getLottoNumber"
    ): Response<LottoResponse>
}