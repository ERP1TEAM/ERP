package com.quickkoala.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class GetToken {

	private static JwtTokenProvider jwtTokenProvider;
	
	@Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        GetToken.jwtTokenProvider = jwtTokenProvider;
    }

    public static String getManagerName(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String manager = jwtTokenProvider.getName(token);
        return manager;
    }
    
    public static String getCompanyCode(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String companyCode = jwtTokenProvider.getCode(token);
        return companyCode;
    }
    
    
}
