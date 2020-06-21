package com.keb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keb.service.EmailLoginService;
import com.keb.service.KakaoLoginService;
import com.keb.service.PracticeService;
 
@Controller
@RequestMapping("/sns")
public class LoginController {
	@Autowired
	KakaoLoginService kakaoLoginService;
	
	@Autowired
	EmailLoginService emailLoginService;
    
    @RequestMapping(value="/fb_login", method = RequestMethod.GET)
    public String fbLogin(Model model) throws Exception {
    	model.addAttribute("facebook_app_id", PracticeService.getKeys().get("facebook_app_id"));
        return "sns/fb_login";
    }
    
    @RequestMapping(value="/kt_login", method = RequestMethod.GET)
    public String ktLogin(Model model) throws Exception {
    	model.addAttribute("kakao_javascript_key", PracticeService.getKeys().get("kakao_javascript_key"));
        return "sns/kt_login";
    }
    
    @RequestMapping(value="/kt_login/oauth", method = RequestMethod.GET)
    public String ktOauthLogin(@RequestParam(required=false) String code, @RequestParam(required=false) String error, Model model) throws Exception {
    	model.addAttribute("result", kakaoLoginService.getUserProfile(code, error));
        return "sns/kt_oauth_login";
    }
    
    @RequestMapping(value="/em_login", method = RequestMethod.GET)
    public String emLogin() throws Exception {
    	return "sns/em_login";
    }
    
    @RequestMapping(value="/em_login/send_mail", method = RequestMethod.GET)
    public String sendMail(String email) throws Exception {
    	emailLoginService.sendMail(email);
        return "sns/fb_login";
    }
    
    @RequestMapping(value="/em_login/authorization", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> authrization(String email, String code) throws Exception {
    	Map<String, Object> model = new HashMap<>();
    	model.put("result", emailLoginService.authorization(email, code));
        return model;
    }
}