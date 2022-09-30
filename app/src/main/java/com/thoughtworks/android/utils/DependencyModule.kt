package com.thoughtworks.android.utils

import com.thoughtworks.android.data.source.DataSource
import com.thoughtworks.android.data.source.Repository
import com.thoughtworks.android.utils.schedulers.AndroidSchedulerProvider
import com.thoughtworks.android.utils.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
abstract class DependencyModule {

    @Binds
    abstract fun bindDataSource(dataSourceImpl: Repository): DataSource

    @Binds
    abstract fun bindSchedulerProvider(schedulerProviderImpl: AndroidSchedulerProvider): SchedulerProvider
}