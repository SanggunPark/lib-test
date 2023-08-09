package com.metaverse.world.testlib.repository.remote

import com.metaverse.world.testlib.model.remote.LottoResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun searchLotto(drwNo: Int): Response<LottoResponse>
}