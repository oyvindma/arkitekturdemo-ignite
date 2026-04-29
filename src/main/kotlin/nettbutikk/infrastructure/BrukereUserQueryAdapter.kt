package nettbutikk.infrastructure

import brukere.application.GetUserUseCase
import brukere.application.UserDto
import nettbutikk.core.UserQueryPort
import nettbutikk.core.UserView

/**
 * ACL adapter: translates brukere domain objects into nettbutikk's UserView.
 * Prevents brukere's internal model from leaking into the nettbutikk context.
 */
class BrukereUserQueryAdapter(
    private val getUserUseCase: GetUserUseCase
) : UserQueryPort {

    override fun findUserById(id: String): UserView? {
        return try {
            val dto = getUserUseCase.execute(id)
            toUserView(dto)
        } catch (e: IllegalStateException) {
            null
        }
    }

    override fun userExists(id: String): Boolean {
        return findUserById(id) != null
    }

    private fun toUserView(dto: UserDto): UserView = UserView(
        id = dto.id,
        name = dto.name
    )
}
