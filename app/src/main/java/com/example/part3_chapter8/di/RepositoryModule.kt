package com.example.part3_chapter8.di

import com.example.part3_chapter8.data.repository.ContentRepositoryImpl
import com.example.part3_chapter8.data.source.local.dao.ContentDao
import com.example.part3_chapter8.data.source.remote.api.ContentService
import com.example.part3_chapter8.domain.repository.ContentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesContentRepository(
        contentService: ContentService,
        contentDao: ContentDao
    ): ContentRepository = ContentRepositoryImpl(contentService, contentDao)
}