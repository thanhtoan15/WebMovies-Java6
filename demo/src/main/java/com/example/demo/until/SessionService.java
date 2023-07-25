package com.example.demo.until;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    HttpSession httpSession;

    public <T> T getsession(String name){
        if(httpSession.getAttribute(name)!=null){
            return(T)httpSession.getAttribute(name);
        }
        return null;
    }
    public void addsession(String name , Object values){
        httpSession.setAttribute(name,values);
    }
    public void removesession(String name){
        httpSession.removeAttribute(name);
    }
}
