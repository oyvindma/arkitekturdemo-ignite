package brukere.presentation

import brukere.application.DeleteUserUseCase
import brukere.application.GetUserUseCase
import brukere.application.ListUsersUseCase
import brukere.application.RegisterUserUseCase
import brukere.application.UpdateUserUseCase
import brukere.application.UserDto

class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val listUsersUseCase: ListUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {

    fun registerUser(name: String): UserDto {
        return registerUserUseCase.execute(RegisterUserUseCase.Command(name = name))
    }

    fun listUsers(): List<UserDto> {
        return listUsersUseCase.execute()
    }

    fun getUser(userId: String): UserDto {
        return getUserUseCase.execute(userId)
    }

    fun updateUser(userId: String, name: String): UserDto {
        return updateUserUseCase.execute(UpdateUserUseCase.Command(userId = userId, name = name))
    }

    fun deleteUser(userId: String) {
        deleteUserUseCase.execute(userId)
    }
}
