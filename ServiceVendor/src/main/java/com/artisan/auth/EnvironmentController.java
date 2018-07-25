package com.artisan.auth;

import com.artisan.transmit.slot.FlumeBootStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

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

    @Resource
    private RestTemplate restTemplate;

    @Resource
    AuthSl authSl;
    @RequestMapping(value = "showHost",method = RequestMethod.GET)
    @ResponseBody
    public String showHost(HttpServletRequest request){
        log.info("主机展示被调用");
        String loginInfo = request.getHeader("loginName");
        log.info("loginName传递：{}。", loginInfo);
        String hostAddress;
        try {
            InetAddress address = InetAddress.getLocalHost();
            hostAddress = address.getHostAddress();
        }catch (Exception e){
            log.info("获取主机出现错误", e);
            hostAddress = "unKnown";
        }
        FlumeBootStream.addParameter("artist-MARS","222222222");
        FlumeBootStream.addParameter("artist-MARS1","wwwwwww");
        FlumeBootStream.addParameter("artist-MARS2","eeeeeeee");
        FlumeBootStream.addParameter("artist-MARS4","rrrrrfrr");
        FlumeBootStream.addParameter("artist-MARS5","ttttttfft");

        FlumeBootStream.addParameter("CTTc-MARS6","dfffffff");
        FlumeBootStream.addParameter("CTTc-MARS7","fffffffff");



        return authSl.show();
    }


    @RequestMapping(value = "source",method = RequestMethod.GET)
    public String reDirect(HttpServletResponse response,RedirectAttributes attr) {
        log.info("重定向入口");

        attr.addAttribute("test","test");

        return "redirect:/envInfo/target";
    }


    @RequestMapping(value = "target",method = RequestMethod.GET)
    @ResponseBody
    public String  targetDirect(HttpServletRequest request){

        Map<String,String>  ti = FlumeBootStream.getParameterKeyLowerCase();
        Set<String> keySet = ti.keySet();
        if(ti.isEmpty()){
            return "error";
        }
        for(String key:keySet){
            log.info("获取到键：{}，值：{}。",key,ti.get(key));
        }
        return "success";
    }
}
