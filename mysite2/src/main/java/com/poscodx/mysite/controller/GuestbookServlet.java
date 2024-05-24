package com.poscodx.mysite.controller;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GuestbookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("a");

        if("delete".equals(action)) {
            String password = req.getParameter("password");
            Long no = Long.parseLong(req.getParameter("no"));

            new GuestbookDao().delete(no, password);
            resp.sendRedirect(req.getContextPath() + "/guestbook");
        } else if("deleteform".equals(action)) {
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
            rd.forward(req, resp);
        } else if ("add".equals(action)) {
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            String contents = req.getParameter("contents");

            GuestbookVo vo = new GuestbookVo();
            vo.setName(name);
            vo.setPassword(password);
            vo.setContents(contents);

            new GuestbookDao().insert(vo);

            resp.sendRedirect(req.getContextPath() + "/guestbook");

        } else {
            List<GuestbookVo> list = new GuestbookDao().findAll();

            req.setAttribute("list", list);
            req.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
