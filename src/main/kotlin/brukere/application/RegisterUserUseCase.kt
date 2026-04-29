package brukere.application

import brukere.core.User
import brukere.core.UserRepository
import java.util.UUID

class RegisterUserUseCase(private val userRepository: UserRepository) {

    data class Command(val name: String)

    fun execute(command: Command): UserDto {
        val user = User(
            id = UUID.randomUUID().toString(),
            name = command.name
        )
        userRepository.save(user)
        return toDto(user)
    }
}

fun toDto(user: User): UserDto = UserDto(
    id = user.id,
    name = user.name,
    borrowedToolIds = user.borrowedToolIds
)
