package brukere.application

import brukere.core.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    fun execute(userId: String): UserDto {
        val user = userRepository.findById(userId)
            ?: error("User with id $userId not found")
        return toDto(user)
    }
}
