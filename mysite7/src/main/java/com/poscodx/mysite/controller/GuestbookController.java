package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;


	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/index";
	}

	@RequestMapping("/add")
	public String index(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value="/delete/{no2}", method=RequestMethod.GET)
	public String delete(@PathVariable("no2") Long no) {
		return "guestbook/delete";
	}

	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteContents(no, password);
		return "redirect:/guestbook";
	}
}