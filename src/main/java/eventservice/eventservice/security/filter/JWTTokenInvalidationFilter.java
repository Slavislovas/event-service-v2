package eventservice.eventservice.security.filter;

import eventservice.eventservice.business.repository.InvalidTokenRepository;
import eventservice.eventservice.business.repository.model.InvalidTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JWTTokenInvalidationFilter extends OncePerRequestFilter {
    @Autowired
    InvalidTokenRepository invalidTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        jwt = jwt.replace("Bearer", "");
        InvalidTokenEntity invalidTokenEntity = new InvalidTokenEntity();
        invalidTokenEntity.setToken(jwt);
        invalidTokenRepository.save(invalidTokenEntity);
        SecurityContextHolder.clearContext();
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return !request.getServletPath().equals("/v2/users/logout");
    }
}
