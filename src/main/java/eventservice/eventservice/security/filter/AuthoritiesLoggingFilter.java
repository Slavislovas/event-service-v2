package eventservice.eventservice.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Log4j2
public class AuthoritiesLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            log.info("User " + authentication.getName() + " is succesfully authenticated and has" +
                    " the authorities " + authentication.getAuthorities().toString());
        }
        chain.doFilter(request, response);
    }
}
