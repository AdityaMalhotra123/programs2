package hu.ait.shoppinglist.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ait.shoppinglist.data.AppDatabase
import hu.ait.shoppinglist.data.ShoppingDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTodoDao(appDatabase: AppDatabase): ShoppingDAO {
        return appDatabase.shoppingDao()
    }

    @Provides
    @Singleton
    fun provideTodoAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }
}