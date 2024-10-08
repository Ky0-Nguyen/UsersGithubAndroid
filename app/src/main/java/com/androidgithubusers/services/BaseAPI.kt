import com.androidgithubusers.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BaseAPI object for setting up and providing the API service.
 *
 * This singleton object is responsible for creating and configuring the Retrofit instance
 * used for making API calls to the GitHub API. It provides a lazy-initialized UserService
 * instance that can be used throughout the application for network operations.
 */

object BaseAPI {
    private const val BASE_URL = "https://api.github.com/"

    val api: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}
