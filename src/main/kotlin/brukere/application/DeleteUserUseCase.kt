package brukere.application

import brukere.core.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {

    fun execute(userId: String) {
        require(userRepository.exists(userId)) { "User with id $userId not found" }
        userRepository.delete(userId)
    }
}
