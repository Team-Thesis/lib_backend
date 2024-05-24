package sru.edu.sru_lib_management.auth.domain.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.xbill.DNS.Lookup
import org.xbill.DNS.MXRecord
import org.xbill.DNS.Type
import java.util.*
import java.util.regex.Pattern
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class EmailValidationService {
    val emailPattern: Pattern = Pattern.compile( "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")

    suspend fun validateEmail(email: String): String? {
        if (!isValidFormat(email)) {
            return "Invalid email format."
        }
        if (!hasValidDomain(email)) {
            return "Invalid email domain."
        }
        if (!hasValidSMTP(email)) {
            return "No accessible SMTP server for the email domain."
        }
        return null // Email is valid
    }


    fun isValidFormat(email: String): Boolean{
        return emailPattern.matcher(email).matches()
    }

    private suspend fun hasValidDomain(email: String): Boolean{
        val domain = email.split("@")[1]
        return withContext(Dispatchers.IO) {
            try {
                val lookup = Lookup(domain, Type.MX)
                val records = lookup.run()
                records != null && records.any { it is MXRecord }
            } catch (e: Exception) {
                false
            }
        }
    }

    private suspend fun hasValidSMTP(email: String): Boolean {
        val domain = email.split("@")[1]
        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.$domain"
        properties["mail.smtp.port"] = "25"

        val session = Session.getDefaultInstance(properties)
        return withContext(Dispatchers.IO) {
            try {
                val transport: Transport = session.transport
                transport.connect()
                val message = MimeMessage(session)
                message.setFrom(InternetAddress("verify@yourdomain.com"))
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email))
                transport.sendMessage(message, message.allRecipients)
                transport.close()
                true
            } catch (e: MessagingException) {
                false
            }
        }
    }
}