package com.example.triklandroidassessment.di

import com.example.triklandroidassessment.model.remote.Constants
import com.example.triklandroidassessment.model.remote.apiInterface.QuestionsApi
import com.example.triklandroidassessment.model.remote.repository.QuestionsAnswersRepo
import com.example.triklandroidassessment.model.remote.repositoryImpl.QuestionsAnswersRepoImpl
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        moshi: Moshi
    ): QuestionsApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.MAIN_BASE_URL)
            .client(okHttpClient)
            .build()
            .create(QuestionsApi::class.java)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

@Provides
@Singleton
fun provideMoshi():Moshi=Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttp(
    ) : OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideQuestionsAnswerRepository(api: QuestionsApi): QuestionsAnswersRepo {
        return QuestionsAnswersRepoImpl(api)
    }

}