package brukere

import brukere.application.DeleteUserUseCase
import brukere.application.GetUserUseCase
import brukere.application.ListUsersUseCase
import brukere.application.RegisterUserUseCase
import brukere.application.UpdateUserUseCase
import brukere.core.UserRepository
import brukere.infrastructure.InMemoryUserRepository
import brukere.presentation.UserController

object BrukereFactory {

    fun createUserController(userRepository: UserRepository = InMemoryUserRepository()): UserController {

        val registerUserUseCase = RegisterUserUseCase(userRepository)
        val listUsersUseCase = ListUsersUseCase(userRepository)
        val getUserUseCase = GetUserUseCase(userRepository)
        val updateUserUseCase = UpdateUserUseCase(userRepository)
        val deleteUserUseCase = DeleteUserUseCase(userRepository)

        return UserController(
            registerUserUseCase = registerUserUseCase,
            listUsersUseCase = listUsersUseCase,
            getUserUseCase = getUserUseCase,
            updateUserUseCase = updateUserUseCase,
            deleteUserUseCase = deleteUserUseCase
        )
    }
}
