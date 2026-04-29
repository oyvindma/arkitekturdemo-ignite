package brukere.application

import brukere.core.UserRepository

class UpdateUserUseCase(private val userRepository: UserRepository) {

    data class Command(val userId: String, val name: String)

    fun execute(command: Command): UserDto {
        val existing = userRepository.findById(command.userId)
            ?: error("User with id ${command.userId} not found")
        val updated = existing.copy(name = command.name)
        userRepository.save(updated)
        return toDto(updated)
    }
}
