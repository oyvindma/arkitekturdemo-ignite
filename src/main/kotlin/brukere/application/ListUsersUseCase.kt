package brukere.application

import brukere.core.UserRepository

class ListUsersUseCase(private val userRepository: UserRepository) {

    fun execute(): List<UserDto> {
        return userRepository.findAll().map { toDto(it) }
    }
}
