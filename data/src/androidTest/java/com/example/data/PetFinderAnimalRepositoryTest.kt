package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.api.IPetFinderApi
import com.example.data.api.model.mappers.ApiAnimalMapper
import com.example.data.api.model.mappers.ApiPaginationMapper
import com.example.data.api.utils.FakeServer
import com.example.data.cache.ICache
import com.example.data.cache.PetDatabase
import com.example.data.cache.RoomCache
import com.example.data.di.ModuleCache
import com.example.data.di.ModulePreferences
import com.example.data.preferences.FakePreferences
import com.example.data.preferences.IPreferences
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(ModulePreferences::class, ModuleCache::class)
class PetFinderAnimalRepositoryTest {

    // System under test
    private lateinit var sut: PetFinderAnimalRepository

    // Dependencies
    @Inject
    lateinit var api: IPetFinderApi
    @Inject
    lateinit var preferences: IPreferences
    @Inject
    lateinit var cache: ICache
    @Inject
    lateinit var apiAnimalMapper: ApiAnimalMapper
    @Inject
    lateinit var apiPaginationMapper: ApiPaginationMapper
    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder
    @Inject
    lateinit var database: PetDatabase

    // JUnit rule from the Hilt library. Helps in dependency graph creation, injections, control lifecycles, etc.
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    // Rule to emit results instantly (by room), replaces the background executor of arch components with this
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Using a fake server for responses from the Okhttp's MockWebServer
    private val fakeServer = FakeServer()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestCacheModule {
        @Provides
        @Singleton
        fun providesRoomDbTest(): PetDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                PetDatabase::class.java
            ).allowMainThreadQueries().build()
        }

        @Provides
        fun providePreferences(): IPreferences {
            return FakePreferences()
        }

        @Provides
        @Singleton
        fun provideCache(database: PetDatabase): ICache {
            return RoomCache(database.getAnimalsDao(), database.getOrganizationDao())
        }
    }

    @Before
    fun setup() {
        hiltRule.inject() // This should be called before accessing any @Inject properties

        fakeServer.start()

        preferences.apply {
            deleteTokenInfo()
            putToken("validToken")
            putTokenExpirationTime(
                Instant.now().plusSeconds(3600).epochSecond
            )
            putTokenType("Bearer")
        }

        // Initialize API with the fake server's base URL
        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint) // overriding the endpoint
            .build()
            .create(IPetFinderApi::class.java)

        // Initialize the system under test
        sut = PetFinderAnimalRepository(
            api, cache, apiAnimalMapper, apiPaginationMapper
        )
    }

    @After
    fun teardown() {
        fakeServer.shutdown()
    }

    @Test
    fun requestMoreAnimals_apiCall_foundInDb() {
        runBlocking {
            // Given
            fakeServer.setHappyPathDispatcher()

            // When
            val paginatedAnimals = sut.requestMoreAnimals(1, 10)
            val animal = paginatedAnimals.animals.first()
            sut.saveAnimals(listOf(animal))

            // Then
            val animalsFlow = sut.getAnimals()
            val animalsList = animalsFlow.toList() // Collect the flow to a list

            // Assert that the animal is found in the database
            assertThat(animalsList.first().first().id).isEqualTo(124)
        }
    }
}
