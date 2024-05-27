package com.poscodx.mysite.controller.action.user;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if(authUser == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long no = authUser.getNo();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        new UserDao().updateUserNamePassword(name, password, gender, no);
        if(session != null) {
            session.removeAttribute("authUser");
        }
        authUser.setNo(no);
        authUser.setName(name);
        session.setAttribute("authUser", authUser);

        resp.sendRedirect(req.getContextPath());
    }
}
