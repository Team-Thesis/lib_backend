package sru.edu.sru_lib_management.auth.domain.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService {
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = HashMap()
        return createToken(claims, userDetails)
    }

    fun extractEmail(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val email = extractEmail(token!!)
        return (email == userDetails.username && !isTokenExpire(token))
    }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun createToken(claims: Map<String, Any?>, userDetails: UserDetails): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() * 60 * 60 * 24))
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private val signInKey: SecretKey
        get() {
            val keyBytes = Decoders.BASE64.decode(SECRETE)
            return Keys.hmacShaKeyFor(keyBytes)
        }

    ///
    private fun isTokenExpire(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    companion object {
        const val SECRETE: String = "0L3sLtlyKYBAI4e7P1qGWiPbXbAxmgHCpjmdg9AvweWXYZc/WPRhAe4ztjQ75f3FDg/91hKOxcXN7xOuOXYTrczlwf5HgSesjEf05KTGaoV7YRT9WWytTFaMn9gk/cCZAKMtrPv+AzKb2LTqTJEDJJRI7khHHCZ50D0LOTUaKhRp9Z9/WMaQccydK1LNfsodi7svQyi5E2apgZz9w2iEqAStcTnipA7PKGMFw72GTtDqVUhwvUP1f+6TSsBOI+vuFw1zByI731Pix4XScl+U3jcGpy+ZYoVyPgouz+KZjh7qEyo2FtGPoFD8UNaL07GK48GzjmLtFmsuute7S5Jm1u703PF26YrOO3cR4QeP0jw=\n"
    }
}
