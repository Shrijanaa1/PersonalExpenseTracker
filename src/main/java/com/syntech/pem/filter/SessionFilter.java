//package com.syntech.pem.filter;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author shrijanakarki
// */
//
//public class SessionFilter implements Filter{
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig); 
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(false); //Retrieves an existing session without creating a new one
//        
//        String loginURI = req.getContextPath() + "/login.xhtml";
//        String signUpURI = req.getContextPath() + "/signUp.xhtml";
//        
//        boolean loggedIn = (session != null && session.getAttribute("valid_user") != null);
//        boolean loginRequest = req.getRequestURI().equals(loginURI);
//        boolean signUpRequest = req.getRequestURI().equals(signUpURI);
////        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + "/resources");
//
//        
//        if(loggedIn || loginRequest || signUpRequest ){
//            chain.doFilter(request, response); //User trying to log in/sign up or is logged in 
//        }else{
//            res.sendRedirect(loginURI); //Redirect to login page
//        }
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy(); 
//    }
//    
//}
