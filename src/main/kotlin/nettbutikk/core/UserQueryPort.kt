package nettbutikk.core

/** ACL port: nettbutikk's view of a user from the brukere context. */
data class UserView(val id: String, val name: String)

interface UserQueryPort {
    fun findUserById(id: String): UserView?
    fun userExists(id: String): Boolean
}
