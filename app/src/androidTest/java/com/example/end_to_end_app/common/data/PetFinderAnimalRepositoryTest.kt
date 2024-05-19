package com.example.end_to_end_app.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.end_to_end_app.common.data.api.IPetFinderApi
import com.example.end_to_end_app.common.data.api.model.mappers.ApiAnimalMapper
import com.example.end_to_end_app.common.data.api.utils.FakeServer
import com.example.end_to_end_app.common.data.cache.ICache
import com.example.end_to_end_app.common.data.cache.PetDatabase
import com.example.end_to_end_app.common.data.cache.RoomCache
import com.example.end_to_end_app.common.data.di.ModuleCache
import com.example.end_to_end_app.common.data.di.ModulePreferences
import com.example.end_to_end_app.common.data.preferences.FakePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton


@HiltAndroidTest
@UninstallModules(ModulePreferences::class, ModuleCache::class)
class PetFinderAnimalRepositoryTest{

    // system under test
    private lateinit var sut: PetFinderAnimalRepository

    // dependencies
    private lateinit var api: IPetFinderApi
    private lateinit var cache: ICache
    @Inject
    lateinit var apiAnimalMapper: ApiAnimalMapper

    // junit rule from the hilt lib. Helps in - dep graph creation, injections, control lifecycles etc
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    // rule to emit results instantly (by room), replaces the bg executor of arch comp with this
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // as the ModuleCache is uninstalled, you can't use its deps. Instead use the following test module.
    @Inject
    lateinit var database: PetDatabase

    // uses an inMemory database for storage
    @Module @InstallIn(SingletonComponent::class)
    object TestCacheModule{
        @Provides @Singleton
        fun providesRoomDbTest(): PetDatabase{
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                PetDatabase::class.java
            ).allowMainThreadQueries().build()
        }
    }

    /**
     * as the ModulePreferences is removed, (other way of build test dep graph - instead of creating
     * a new module), using a custom map-based preferences for authInterceptor
     */

    @BindValue
    val preferences = FakePreferences()

    /**
     * using a fake server for responses from the Okhttp's MockWebServer
     */
    private val fakeServer = FakeServer()

    @Before
    fun setup(){
        fakeServer.start()
        preferences.apply {
            deleteTokenInfo()
            putToken("validToken")
            putTokenExpirationTime(
                Instant.now().plusSeconds(3600).epochSecond
            )
            putTokenType("Bearer")
        }
        hiltRule.inject()

        //deps
        cache = RoomCache(database.getAnimalsDao(), database.getOrganizationDao())

    }

}