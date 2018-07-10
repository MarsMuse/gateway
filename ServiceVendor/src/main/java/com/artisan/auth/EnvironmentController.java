package com.artisan.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Enumeration;

/**
 *
 * @author xz man
 * @date 2018/7/10 下午4:15
 * 环境信息
 *
 */
@Controller
@RequestMapping(value = "envInfo")
public class EnvironmentController {

    private Logger log = LoggerFactory.getLogger(EnvironmentController.class);

    @RequestMapping(value = "showHost",method = RequestMethod.GET)
    @ResponseBody
    public String showHost(){
        log.info("主机展示被调用");

        String hostAddress;
        try {
            InetAddress address = InetAddress.getLocalHost();
            hostAddress = address.getHostAddress();
        }catch (Exception e){
            log.info("获取主机出现错误", e);
            hostAddress = "unKnown";
        }
        return hostAddress;
    }


    @RequestMapping(value = "source",method = RequestMethod.GET)
    public String reDirect(RedirectAttributes attr) {
        log.info("重定向入口");

        attr.addAttribute("test","test");

        return "redirect:/envInfo/target";
    }


    @RequestMapping(value = "target",method = RequestMethod.GET)
    @ResponseBody
    public String  targetDirect(HttpServletRequest request){
        Enumeration<String> atta = request.getAttributeNames();
        while (atta.hasMoreElements()){
            System.out.println(atta.nextElement());
        }

        Enumeration<String> ptta = request.getParameterNames();
        while (ptta.hasMoreElements()){
            System.out.println(ptta.nextElement());
        }
        log.info("接收重定向");
        return "重定向成功";

    }
}
