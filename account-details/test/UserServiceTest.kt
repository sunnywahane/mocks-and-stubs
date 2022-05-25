import exceptions.NotFoundException
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import models.EmailBody
import models.User
import repository.UserRepository
import service.EmailService
import service.UserService


class UserServiceTest: StringSpec() {

    init {
        "testing welcome email service" {
            val dummyService = mockk<EmailService>(relaxed=true)
            val userService = UserService(null, dummyService)
            userService.sendWelcomeEmail("test@gmail.com")
            val expectedEmailBody = EmailBody("Welcome", "Welcome to the portal", "test@gmail.com")
            verify(exactly = 1) {
                dummyService.send(expectedEmailBody)
            }
        }

        "should send users account details" {
            val dummyService = mockk<EmailService>(relaxed=true)
            val dummyUserRepository = mockk<UserRepository>()

            every {
                dummyUserRepository.findByEmail("test@gmail.com")
            } returns User("1111111111", "test@gmail.com", "test")

            val userService = UserService(dummyUserRepository, dummyService)
            userService.sendRegisteredPhoneNumber("test@gmail.com")

            val expectedEmailBody = EmailBody("Account Details", "Here is your Registered Phone Number: 1111111111", "test@gmail.com")

            verify(exactly = 1) {
                dummyService.send(expectedEmailBody)
            }
        }

        "should send account not found mail" {
            val dummyService = mockk<EmailService>(relaxed=true)
            val dummyUserRepository = mockk<UserRepository>()

            every {
                dummyUserRepository.findByEmail("failtest@gmail.com")
            }.throws(NotFoundException())

            val userService = UserService(dummyUserRepository, dummyService)


            userService.sendRegisteredPhoneNumber("failtest@gmail.com")
            val expectedEmailBody = EmailBody("Account Not Found", "We do not have a registered account matching your email address", "failtest@gmail.com")

            verify(exactly = 1) {
                dummyService.send(expectedEmailBody)
            }
        }

    }
}
