package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.mysite.controller.ActionServlet.Action;

public class WriteAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if(authUser == null) {
            response.sendRedirect(request.getContextPath() + "/board");
            return;
        }

        Integer maxGroupNo = new BoardDao().findMaxGroupNo();
        BoardVo boardVo = new BoardVo();
        boardVo.setTitle(request.getParameter("title"));
        boardVo.setContents(request.getParameter("contents"));
        boardVo.setHit(0);
        boardVo.setDepth(0);
        boardVo.setGroupNo(maxGroupNo + 1);
        boardVo.setOrderNo(1);
        boardVo.setUserNo(authUser.getNo());

        new BoardDao().insert(boardVo);

        response.sendRedirect( request.getContextPath() + "/board?a=board");
    }

}