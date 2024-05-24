package sru.edu.sru_lib_management.auth.domain.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtTokenSupport {
    private val secretKey = Keys.hmacShaKeyFor(SECRET.toByteArray())
    private val phaser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    fun generateToken(username: String): BearerToken {
        val claim: Map<String, Any> = HashMap()
        val token = Jwts.builder()
            .setClaims(claim)
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
            .signWith(secretKey)
            .compact()
        return BearerToken(token)
    }

    fun extractUsername(token: BearerToken): String{
        return phaser
            .parseClaimsJws(token.value)
            .body.subject
    }

    fun isValidToken(token: BearerToken, user: UserDetails?): Boolean{
        val claims = phaser.parseClaimsJws(token.value).body
        val unexpired = claims.expiration.after(Date.from(Instant.now()))

        return unexpired && (claims.subject == user?.username)
    }

    companion object {
        const val SECRET: String = "0L3sLtlyKYBAI4e7P1qGWiPbXbAxmgHCpjmdg9AvweWXYZc/WPRhAe4ztjQ75f3FDg/91hKOxcXN7xOuOXYTrczlwf5HgSesjEf05KTGaoV7YRT9WWytTFaMn9gk/cCZAKMtrPv+AzKb2LTqTJEDJJRI7khHHCZ50D0LOTUaKhRp9Z9/WMaQccydK1LNfsodi7svQyi5E2apgZz9w2iEqAStcTnipA7PKGMFw72GTtDqVUhwvUP1f+6TSsBOI+vuFw1zByI731Pix4XScl+U3jcGpy+ZYoVyPgouz+KZjh7qEyo2FtGPoFD8UNaL07GK48GzjmLtFmsuute7S5Jm1u703PF26YrOO3cR4QeP0jw=\n"
    }
}
