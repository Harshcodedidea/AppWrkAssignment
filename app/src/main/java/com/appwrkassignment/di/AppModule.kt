package com.appwrkassignment.di

import android.content.Context
import com.appwrkassignment.base.BasePrefsManager
import com.appwrkassignment.managers.UserProfilePrefs
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    /**
     * Provides the application context.
     * @param context Application context
     * @return Context instance
     */
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    /**
     * Provides a Gson instance for JSON serialization and deserialization.
     * @return Gson instance
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    /**
     * Provides a BasePrefsManager instance for managing shared preferences.
     * @param context Application context
     * @return BasePrefsManager instance configured with the provided context
     */
    @Provides
    @Singleton
    fun provideBasePrefsManager(context: Context): BasePrefsManager {
        return BasePrefsManager.with(context)
    }

    @Provides
    @Singleton
    fun provideUserProfilePrefs(
        @ApplicationContext context: Context
    ): UserProfilePrefs = UserProfilePrefs(context)
}