package GameRanks.GameRanks.interceptor;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.service.UserService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<User.AccessLevel> routeRoles = getAccessLevels((HandlerMethod) handler);
        User user = userService.getUser();

        //Ha az utvonalon nincs korlatozas, akkor tovabbengedjuk a felhasznalot
        if (routeRoles.isEmpty()) {
            return true;
        }
        //Ha van, akkor megnezzuk, hogy megvan e a megfelelo joga
        else if(userService.isLoggedIn() && routeRoles.contains(user.getAccessLevel())) {
            return true;
        }
        
        response.setStatus(401);
        return false;
    }

    private List<User.AccessLevel> getAccessLevels(HandlerMethod handler) {
        AccessBy accessLevel = handler.getMethodAnnotation(AccessBy.class);
        return accessLevel == null ? Collections.emptyList() : Arrays.asList(accessLevel.value());
    }
}
