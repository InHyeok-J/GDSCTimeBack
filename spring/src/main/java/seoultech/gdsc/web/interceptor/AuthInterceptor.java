package seoultech.gdsc.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.serializer.EmptyJsonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final String[] ExceptionController =
            {"GET/api/user", "POST/api/user"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler)
            throws Exception {

        //path가 중복되는 것은 예외처리로 제외함
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        if(Arrays.asList(ExceptionController).contains((method + requestUrl))){
            return true;
        }

        //세션이 있는지 체크 없으면 401 리턴
        HttpSession session = request.getSession();
        Object user = session.getAttribute("sessionId");
         if(ObjectUtils.isEmpty(user)){
             FailResponse fail = new FailResponse<>("로그인이 필요합니다",new EmptyJsonResponse());
             ObjectMapper objectMapper = new ObjectMapper();
             String result = objectMapper.writeValueAsString(fail);
             response.setStatus(401);
             response.setContentType("application/json");
             response.setCharacterEncoding("utf-8");
             response.getWriter().write(result);
             return false;
         }else{
             return true;
         }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
