package com.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("url: "+request.getRequestURI());
        // 如果是登陆页面则放行
        if (request.getRequestURI().contains("login")){
            return true;
        }else {

            HttpSession session = request.getSession();
            // 如果用户已登陆也放行
            if (session.getAttribute("user") != null) {
                return true;
            } else {
                //获取当前请求的路径
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
                if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                    //告诉ajax我是重定向
                    response.setHeader("REDIRECT", "REDIRECT");
                    //告诉ajax我重定向的路径
                    response.setHeader("CONTEXTPATH", basePath + "/login.jsp");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }else {
                    // 用户没有登陆重定向到登录页
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
