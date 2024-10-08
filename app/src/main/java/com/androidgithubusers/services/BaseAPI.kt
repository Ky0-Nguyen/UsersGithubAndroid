import com.androidgithubusers.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
