package com.poscodx.mysite.interceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {

    private SiteService siteService;

    public SiteInterceptor(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("siteVo");
        if (siteVo == null) {
            siteVo = siteService.getSite();
            request.getServletContext().setAttribute("siteVo", siteVo);
        }

        return true;
    }
}
