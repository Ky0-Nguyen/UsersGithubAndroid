package com.androidgithubusers.repository
import com.androidgithubusers.models.User
import com.androidgithubusers.models.UserDetail
import com.androidgithubusers.services.UserService

/**
 * Repository class for managing user-related data operations.
 *
 * This class serves as an abstraction layer between the data sources (API and local database)
 * and the rest of the application. It provides methods to fetch user data and details.
 *
 * @property api The UserService instance used for making API calls.
 */

class UserRepository(
    private val api: UserService
) {
    /**
     * Fetches a list of users from the API.
     *
     * This method retrieves a list of GitHub users starting from the specified 'since' ID.
     * It uses the UserService to make an API call and return the results.
     *
     * @param since The user ID to start fetching from. Users with an ID greater than this will be returned.
     * @return A List of User objects representing the fetched GitHub users.
     * @throws Exception if there's an error during the API call or data processing.
     */
    suspend fun fetchUsersFromApi(since: Int): List<User> {
        return api.fetchUsers(since = since)
    }

    /**
     * Retrieves detailed information about a specific user.
     *
     * This method fetches comprehensive details about a GitHub user identified by their login name.
     * It utilizes the UserService to make an API call to get the user's detailed information.
     *
     * @param login The login name (username) of the GitHub user.
     * @return A UserDetail object containing detailed information about the specified user.
     * @throws Exception if there's an error during the API call or data processing.
     */
    suspend fun getUserDetail(login: String): UserDetail {
        return api.getUserDetail(login)
    }
}
