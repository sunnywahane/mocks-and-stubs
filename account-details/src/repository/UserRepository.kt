package repository

import exceptions.NotFoundException
import models.User

interface UserRepository {
  @Throws(NotFoundException::class)
  fun findByEmail(email: String?): User
}
