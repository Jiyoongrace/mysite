package com.poscodx.mysite.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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

    @Auth
    @RequestMapping("/delete/{no}")
    public String delete(
            @AuthUser UserVo authUser,
            @PathVariable("no") Long boardNo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        boardService.deleteContents(boardNo, authUser.getNo());
        return "redirect:/board?p=" + page + "&kwd=" + encodedKeyword;
    }

    @Auth
    @RequestMapping("/modify/{no}")
    public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
        BoardVo boardVo = boardService.getContents(no, authUser.getNo());
        model.addAttribute("boardVo", boardVo);
        return "board/modify";
    }

    @Auth
    @RequestMapping(value="/modify", method=RequestMethod.POST)
    public String modify(
            @AuthUser UserVo authUser,
            BoardVo boardVo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        boardVo.setUserNo(authUser.getNo());
        boardService.modifyContents(boardVo);

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        return "redirect:/board/view/" + boardVo.getNo() +
                "?p=" + page +
                "&kwd=" + encodedKeyword;
    }

    @Auth
    @RequestMapping(value="/write", method=RequestMethod.GET)
    public String write(@AuthUser UserVo authUser) {
        return "board/write";
    }

    @Auth
    @RequestMapping(value="/write", method=RequestMethod.POST)
    public String write(
            @AuthUser UserVo authUser,
            @ModelAttribute BoardVo boardVo,
            @RequestParam(value="p", required=true, defaultValue="1") Integer page,
            @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {

        boardVo.setUserNo(authUser.getNo());
        boardService.addContents(boardVo);

        String encodedKeyword = encodeURL(keyword, "UTF-8");

        return	"redirect:/board?p=" + page + "&kwd=" + encodedKeyword;
    }


    @Auth
    @RequestMapping(value="/reply/{no}")
    public String reply(
            @AuthUser UserVo authUser,
            @PathVariable("no") Long no,
            Model model) {
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