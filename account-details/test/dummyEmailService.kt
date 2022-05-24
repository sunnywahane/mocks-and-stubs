import models.EmailBody
import service.EmailService
import java.io.File

class dummyEmailService : EmailService {

    var lastEmailBody: EmailBody? = null

    override fun send(emailBody: EmailBody?) {
        lastEmailBody = emailBody
    }

    fun getLastEmail(): EmailBody? {
        return this.lastEmailBody
    }
}
