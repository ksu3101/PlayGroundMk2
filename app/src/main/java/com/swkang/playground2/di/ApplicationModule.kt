package com.swkang.playground2.di

import android.content.Context
import com.swkang.playground2.base.helper.ResourceHelper
import com.swkang.playground2.base.helper.ResourceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * @author burkd
 */
@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Singleton
    @Provides
    fun provideResourceHelper(
        @ApplicationContext context: Context
    ): ResourceHelper = ResourceHelperImpl(context)
}
