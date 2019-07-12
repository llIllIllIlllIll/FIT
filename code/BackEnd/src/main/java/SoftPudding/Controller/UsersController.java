package SoftPudding.Controller;
import SoftPudding.Entity.User;
import SoftPudding.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@CrossOrigin(origins = "*" ,maxAge = 3600)
@Controller
@RequestMapping(path="/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String account, @RequestParam String password, HttpServletRequest request,
                                      HttpServletResponse response) {

        HttpSession session = request.getSession();
        String ss = userService.Login(account,password);
        if (ss.equals("100")) {
            Integer userId = userService.findByTel(account).getId();
            if(!request.isRequestedSessionIdValid() || session.getAttribute("USERID")==null) {
                System.out.println("No session existed. Make a new session!");
                session.setAttribute("USERID",userId);
                System.out.println("NEW sessionId:  " + session.getId());
                System.out.println("NEW session的USERID:  " + session.getAttribute("USERID"));
                response.addCookie(new Cookie("USERID", account));
                return (ss );
            }
            else {
                System.out.println("Session exist!  " + session.getId() +" 上一个USERID是 "+session.getAttribute("USERID"));
                session.setAttribute("USERID",userId);
                response.addCookie(new Cookie("USERID", account));
                return (ss );
            }
        }
        return ss;             // 记得改回来 这是测试用的
    }

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/register")
    public @ResponseBody String register(@RequestParam String tel, @RequestParam String password, @RequestParam String nickName) {
        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        user.setNickName(nickName);
        user.setIsactive(true);
        return (userService.register(user));  // 记得改回来 这是测试用的  改了已经
    }

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/changePassword")    // 记得改回来 这是测试用的  改了已经
    public @ResponseBody String changPwd(@RequestParam String tel, @RequestParam String password, HttpServletRequest request,
                                         HttpServletResponse response) {
        HttpSession session = request.getSession();
        System.out.println("session 里面的USERID是：  "+ session.getAttribute("USERID"));
        //if (tel == )
        return userService.changePwd(tel, password);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/logout")
    public @ResponseBody String logOut( HttpServletRequest request,
                                         HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        if (session.getAttribute("USERID" ) == null) {
            System.err.println("没有session啊！");
            throw new Exception("Throw 一个: 没有sesssion");
        }
        else {
            session.setAttribute("USERID",null);
            return "logout succeeded!";
        }
    }

    /*
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping(path = "/changeUserInfo")
    public @ResponseBody boolean changeUserInfo(@RequestBody JSONObject data, HttpServletRequest request,
                                                HttpServletResponse response) {
    }
    */
}