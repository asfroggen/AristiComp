package com.esaudev.aristicomp.auth.di

import com.esaudev.aristicomp.auth.repository.AuthRepository
import com.esaudev.aristicomp.auth.ui.login.middleware.LoginNetworkingMiddleware
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MiddlewareModule {

    @Provides
    @Singleton
    fun provideLoginNetworkMiddleware(
        loginRepository: AuthRepository
    ): LoginNetworkingMiddleware{
        return LoginNetworkingMiddleware(
            loginRepository
        )
    }
}