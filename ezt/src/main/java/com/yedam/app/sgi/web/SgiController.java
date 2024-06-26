package com.yedam.app.sgi.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.sgu.service.SignUpService;
import com.yedam.app.usr.service.UserVO;

import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
public class SgiController {
	@Autowired
	SignUpService signUpService;
	
	@GetMapping("main")
	public String all() {
		return "sgi/loginTest";
	}
	
	@GetMapping("user")
	public void user() {}
	
	@GetMapping("worker")
	public void worker() {}
	
	@GetMapping("admin")
	public void admin() {}
	
	@GetMapping("login")
	public String login() {
		return "sgi/login";
	}
	
	@GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login/loginForm";
    }
	
	@GetMapping("signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userVO", new UserVO());
		return "sgu/signUp";
	}
    
    @PostMapping("signUp")
    public String signUp(@Valid UserVO userVO, Errors errors, Model model, HttpServletResponse resp) {
    	 if(!userVO.getUsersPw().equals(userVO.getUsersPwCheck())) {
         	errors.rejectValue("usersPwCheck", "error", "비밀번호가 다릅니다");
         	
         }
        if (errors.hasErrors()) {
            // 회원가입 실패시 입력 데이터값을 유지
            model.addAttribute("userVO", userVO);
            
//            Map<String, String> validatorResult = signUpService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
            // 회원가입 페이지로 다시 리턴
            //resp.setContentType("text/html; charset=utf-8");
            //  resp.getWriter().append("<script>").append("alert('가입완료!');").append("location.href='sgi/login';</script>");
            return "sgu/signUp";
        }
        //  주석풀어야 회원가입됨
        //  signUpService.joinUser(userVO);
        model.addAttribute("msg", "회원가입 완료!");
        model.addAttribute("url", "sgi/login");
        return "gongtong/message";
    }
    
    //아이디 중복체크
    @GetMapping("signUp/checkId/{id}")
    @ResponseBody
    public String checkId(@PathVariable String id) {
    	UserVO vo = new UserVO();
    	vo.setUsersId(id);
    	return signUpService.idChk(vo);
    }
    
    //닉네임 중복체크
    @GetMapping("signUp/checkNick/{nick}")
    @ResponseBody
    public String checkNick(@PathVariable String nick) {
    	UserVO vo = new UserVO();
    	vo.setUsersNick(nick);
    	return signUpService.nickChk(vo);
    }
    
    //이메일 중복체크
    @GetMapping("signUp/checkEmail")
    @ResponseBody
    public String checkEmail(String email) {
    	UserVO vo = new UserVO();
    	vo.setUsersEmail(email);
    	return signUpService.emailChk(vo);
    }
    
    @GetMapping("findIdPw")
    public String findIdPw() {
	    return "sgi/findIdPw";
    }
      
      
}