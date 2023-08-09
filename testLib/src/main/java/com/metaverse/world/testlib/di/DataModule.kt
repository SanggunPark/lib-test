package com.metaverse.world.testlib.di

import com.metaverse.world.testlib.repository.remote.RemoteDataSource
import com.metaverse.world.testlib.repository.remote.RemoteDataSourceImpl
import com.metaverse.world.testlib.api.LottoAPI
import org.koin.dsl.module
import retrofit2.Retrofit


val remoteDataModule = module {


    single {
        get<Retrofit>().create(LottoAPI::class.java)
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(api = get())
    }
}

