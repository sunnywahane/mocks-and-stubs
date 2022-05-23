package service

import models.EmailBody

interface EmailService {
    fun send(emailBody: EmailBody?)
}
