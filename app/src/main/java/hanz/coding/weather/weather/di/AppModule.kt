package hanz.coding.weather.weather.di

import com.google.android.gms.location.LocationServices
import hanz.coding.weather.weather.data.LocationTrackerImpl
import hanz.coding.weather.weather.data.remote.WeatherApi
import hanz.coding.weather.weather.data.repository.WeatherRepositoryImpl
import hanz.coding.weather.weather.domain.WeatherRepository
import hanz.coding.weather.weather.domain.location.LocationTracker
import hanz.coding.weather.weather.presentation.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single {
        Retrofit.Builder().baseUrl(
            "https://api.open-meteo.com/"
        ).client(get()).addConverterFactory(MoshiConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }
    viewModelOf(::WeatherViewModel)

    single { LocationTrackerImpl(get(), get()) }.bind<LocationTracker>()

    single { WeatherRepositoryImpl(get()) }.bind<WeatherRepository>()
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }
}