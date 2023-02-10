package eventservice.eventservice.business.scheduler;

import eventservice.eventservice.business.repository.InvalidTokenRepository;
import eventservice.eventservice.business.repository.model.InvalidTokenEntity;
import eventservice.eventservice.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class ExpiredInvalidJwtTokenCleanupScheduler {
    @Autowired
    InvalidTokenRepository invalidTokenRepository;

    @Scheduled(cron = "0 * * * * *")
    public void expiredInvalidTokenCleanup(){
        for (InvalidTokenEntity invalidTokenEntity : invalidTokenRepository.findAll()){
            try{
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(invalidTokenEntity.getToken())
                        .getBody();
            }
            catch (ExpiredJwtException ex){
                invalidTokenRepository.delete(invalidTokenEntity);
            }
        }
    }
}
