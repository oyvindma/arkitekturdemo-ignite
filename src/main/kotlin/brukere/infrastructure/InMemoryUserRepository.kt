package brukere.infrastructure

import brukere.core.User
import brukere.core.UserRepository

class InMemoryUserRepository : UserRepository {

    private val store: MutableMap<String, User> = mutableMapOf()

    override fun save(user: User) {
        store[user.id] = user
    }

    override fun findById(id: String): User? = store[id]

    override fun findAll(): List<User> = store.values.toList()

    override fun findByName(name: String): List<User> {
        return store.values.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun delete(id: String) {
        store.remove(id)
    }

    override fun exists(id: String): Boolean = store.containsKey(id)
}
