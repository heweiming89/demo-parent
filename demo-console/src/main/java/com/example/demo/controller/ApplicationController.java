package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.web.constant.VerifyCodeConstant;
import com.example.demo.web.util.VerifyCodeUtils;

@Controller
public class ApplicationController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@GetMapping(value = "/login")
	public String login(Model model) {
		String viewName = "login";
		return viewName;
	}

	@PostMapping(value = "/logout")
	public String logout() {
		return "redirect:/login";
	}

	@GetMapping(value = "/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(RandomUtils.nextInt(4, 6));

		// 存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute(VerifyCodeConstant.VERIFIY_CODE, verifyCode.toLowerCase());
		// 生成图片
		int w = 110, h = 41;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	@PostMapping(value = "/verifyCode")
	@ResponseBody
	public boolean checkVerifyCode(HttpSession session, String verifyCode) throws IOException {
		if (verifyCode == null || verifyCode.length() <= 3) {
			return false;
		}
		if (verifyCode.equalsIgnoreCase((String) session.getAttribute(VerifyCodeConstant.VERIFIY_CODE))) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping(value = "/index")
	public ModelAndView index(HttpSession session) {
		String viewName = "index";
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

}
