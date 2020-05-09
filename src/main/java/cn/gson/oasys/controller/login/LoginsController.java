package cn.gson.oasys.controller.login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/")
@Controller
public class LoginsController {


    public static final String CAPTCHA_KEY = "session_captcha";


    /**
     * 登录请求：
     *      http://localhost:8088/logins
     *      解析login.ftl响应浏览器登录界面
     * @return
     */
    @RequestMapping(value="logins",method=RequestMethod.GET)
    public String login() {
        return "login/login";
    }

    /**
     * 登录界面，验证码图片加载请求：
     *      http://localhost:8088/captcha
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        // 生成图片
        int w = 135, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

        // 将验证码存储在session以便登录时校验
        session.setAttribute(CAPTCHA_KEY, verifyCode.toLowerCase());
    }
}
