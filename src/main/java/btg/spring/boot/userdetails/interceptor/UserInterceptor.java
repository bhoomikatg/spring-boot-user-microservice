package btg.spring.boot.userdetails.interceptor;

import btg.spring.boot.userdetails.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    LoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
        if (request.getMethod().equals("GET") &&
                ((!request.getRequestURI().contains("invalidEmpId")) || (!request.getRequestURI().contains("fallback")))) {
            String[] requestURI = request.getRequestURI().split("/");
            String employeeId = requestURI[requestURI.length - 1];
            Pattern pattern = Pattern.compile("\\d+");
            if (!pattern.matcher(employeeId).matches()) {
                response.sendRedirect("/user/invalidEmpId");
            }
        }
        return true;
    }
}
