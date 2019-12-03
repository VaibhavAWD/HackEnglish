package com.vaibhavdhunde.app.hackenglish.application

import android.app.Application
import com.vaibhavdhunde.app.hackenglish.api.HackEnglishApi
import com.vaibhavdhunde.app.hackenglish.api.NetworkInterceptor
import com.vaibhavdhunde.app.hackenglish.data.DefaultHackEnglishRepository
import com.vaibhavdhunde.app.hackenglish.data.HackEnglishRepository
import com.vaibhavdhunde.app.hackenglish.data.source.remote.UsersRemoteDataSource
import com.vaibhavdhunde.app.hackenglish.util.ViewModelFactory
import io.paperdb.Paper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@Suppress("unused")
class HackEnglishApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@HackEnglishApplication))
        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { HackEnglishApi(instance()) }
        bind() from singleton { UsersRemoteDataSource(instance()) }
        bind<HackEnglishRepository>() with singleton { DefaultHackEnglishRepository(instance()) }
        bind() from provider { ViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        initPaperDb()
    }

    private fun initPaperDb() {
        Paper.init(this)
    }
}