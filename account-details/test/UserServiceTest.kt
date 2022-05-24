import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import models.EmailBody
import service.UserService

class UserServiceTest: StringSpec() {

    init {
        "testing welcome email service" {
            val dummyService = DummyEmailService()
            val userService = UserService(null, dummyService)
            userService.sendWelcomeEmail("test@gmail.com")
            val expectedEmailBody = EmailBody("Welcome", "Welcome to the portal", "test@gmail.com")
            val actualEmailBody = dummyService.getLastEmail()
            actualEmailBody shouldBe expectedEmailBody
        }
    }
}
