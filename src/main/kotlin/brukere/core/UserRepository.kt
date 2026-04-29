package brukere.core

interface UserRepository {
    fun save(user: User)
    fun findById(id: String): User?
    fun findAll(): List<User>
    fun findByName(name: String): List<User>
    fun delete(id: String)
    fun exists(id: String): Boolean
}
