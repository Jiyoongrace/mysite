package com.poscodx.mysite.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping("")
    public String index(
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword,
            Model model) {

        Map<String, Object> map = boardService.getContentsList(page, keyword);

        // model.addAllAttributes(map);
        model.addAttribute("map", map);
        model.addAttribute("keyword", keyword);

        return "board/index";
    }

    @RequestMapping("/view/{no}")
    public String view(@PathVariable("no") Long no, Model model) {
        BoardVo boardVo = boardService.getContents(no);
        model.addAttribute("boardVo", boardVo);
        return "board/view";
    }

    @RequestMapping("/delete/{no}")
    public String delete(
            HttpSession session,
            @PathVariable("no") Long boardNo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        boardService.deleteContents(boardNo, authUser.getNo());
        return "redirect:/board?p=" + page + "&kwd=" + encodedKeyword;
    }

    @RequestMapping("/modify/{no}")
    public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        BoardVo boardVo = boardService.getContents(no, authUser.getNo());
        model.addAttribute("boardVo", boardVo);
        return "board/modify";
    }

    @RequestMapping(value="/modify", method=RequestMethod.POST)
    public String modify(
            HttpSession session,
            BoardVo boardVo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        boardVo.setUserNo(authUser.getNo());
        boardService.modifyContents(boardVo);

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        return "redirect:/board/view/" + boardVo.getNo() +
                "?p=" + page +
                "&kwd=" + encodedKeyword;
    }

    @RequestMapping(value="/write", method=RequestMethod.GET)
    public String write(HttpSession session) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }
        return "board/write";
    }

    @RequestMapping(value="/write", method=RequestMethod.POST)
    public String write(
            HttpSession session,
            @ModelAttribute BoardVo boardVo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        boardVo.setUserNo(authUser.getNo());
        boardService.addContents(boardVo);

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        return	"redirect:/board?p=" + page + "&kwd=" + encodedKeyword;
    }


    @RequestMapping(value="/reply/{no}")
    public String reply(
            HttpSession session,
            @PathVariable("no") Long no,
            Model model) {
        // access control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        BoardVo boardVo = boardService.getContents(no);
        boardVo.setOrderNo(boardVo.getOrderNo() + 1);
        boardVo.setDepth(boardVo.getDepth() + 1);

        model.addAttribute("boardVo", boardVo);

        return "board/reply";
    }

    // encoding
    private String encodeURL(String value, String encoding) {
        try {
            return URLEncoder.encode(value, encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e);
        }
        return value;
    }
}