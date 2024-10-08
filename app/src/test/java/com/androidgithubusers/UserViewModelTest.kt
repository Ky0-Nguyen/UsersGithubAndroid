class UserViewModelTest {

    @Test
    fun testFetchUsers() = runBlocking {
        // Mock repository
        val mockRepository = mock(UserRepository::class.java)
        val testUsers = listOf(
            User(1, "user1", "https://avatar1.url", "https://html1.url"),
            User(2, "user2", "https://avatar2.url", "https://html2.url")
        )
        runBlocking {
            `when`(mockRepository.fetchUsersFromApi(0)).thenReturn(testUsers)
        }

        // Create ViewModel with mocked repository
        val viewModel = UserViewModel(mockRepository)

        // Call the method to test
        viewModel.fetchUsers(0)

        // Verify ViewModel behavior
        verify(mockRepository).fetchUsersFromApi(0)
        
        // Wait for LiveData to be updated
        val liveDataValue = viewModel.users.getOrAwaitValue()

        // Assert the LiveData contains the expected UserEntity objects
        assertEquals(2, liveDataValue.size)
        assertEquals(UserEntity(1, "user1", "https://avatar1.url", "https://html1.url"), liveDataValue[0])
        assertEquals(UserEntity(2, "user2", "https://avatar2.url", "https://html2.url"), liveDataValue[1])
    }
}
