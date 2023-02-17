package com.example.dedicas.di

import android.app.Application
import androidx.room.Room
import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.data_source.local.LocalDatabase
import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.data.repository.AuthTokenRepositoryImpl
import com.example.dedicas.core.util.Constants
import com.example.dedicas.feature_authentication.data.repository.AuthRepository
import com.example.dedicas.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.dedicas.feature_browsing.data.BrowsingRepository
import com.example.dedicas.feature_browsing.data.BrowsingRepositoryImpl
import com.example.dedicas.feature_chat.data.repository.ChatRepository
import com.example.dedicas.feature_chat.data.repository.ChatRepositoryImpl
import com.example.dedicas.feature_profile.data.repository.ProfileRepository
import com.example.dedicas.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.dedicas.feature_request.data.repository.RequestRepository
import com.example.dedicas.feature_request.data.repository.RequestRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRestApi(): RestApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: RestApi, database: LocalDatabase): AuthRepository {
        return AuthRepositoryImpl(api, database.dao)
    }

    @Provides
    @Singleton
    fun provideBrowsingRepository(api: RestApi): BrowsingRepository {
        return BrowsingRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideChatRepository(api: RestApi): ChatRepository {
        return ChatRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: RestApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(api: RestApi): RequestRepository {
        return RequestRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthTokenRepository(database: LocalDatabase): AuthTokenRepository {
        return AuthTokenRepositoryImpl(database.dao)
    }

}