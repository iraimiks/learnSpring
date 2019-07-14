package lv.raimondstest.loansystem.controller;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import lv.raimondstest.loansystem.config.LoadDatabes;
import lv.raimondstest.loansystem.model.LoanApplay;
import lv.raimondstest.loansystem.repo.LoanApplayRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@Controller
@SessionAttributes("loansInSession")
public class LoanApplayController {
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };
    private final LoanApplayRepo repo;
    private static List<LoanApplay> loans = new ArrayList<>();
//    static {
//        loans.add(new LoanApplay(30,"39,39"));
//    }

    public LoanApplayController(LoanApplayRepo repo) {
        this.repo = repo;
    }

//    @GetMapping("/myLoans")
//    private List<LoanApplay> all(){
//
//        return repo.findAll();
//    }
    @GetMapping(path = {"/","/myLoans"})
    public String loanList(Model mode){
        mode.addAttribute("loans",loans);
        return "myLoans";
    }
    @RequestMapping(value ={"/loanwelcome"},method = RequestMethod.GET)
    public String showLoanPage(Model model){
        LoanApplay loanApplay = new LoanApplay();
        model.addAttribute("loanAdd",loanApplay);
        return "loanwelcome";
    }

    @RequestMapping(value = { "/loanwelcome" }, method = RequestMethod.POST)
    public String saveNewLoan(Model model,HttpServletResponse response, HttpServletRequest request, @ModelAttribute("loanAdd") LoanApplay loanApplay){
        int loan = loanApplay.getAmount();

        String loanInstring = Integer.toString(loan);
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime expireDate = date.plusMonths(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Cookie cookie = new Cookie("userip",getClientIpAddress(request));
        response.addCookie(cookie);
        if(loanInstring != null && date != null){
            LoanApplay newLoan = new LoanApplay(loan,expireDate.format(dateTimeFormatter));
            int sum = loans.stream().filter(o->o.getAmount()>0).mapToInt(o->o.getAmount()).sum();

            if(loans.size()<=2){
                loans.add(newLoan);
                log.info(""+loans.toString());
                log.info(""+sum);
                return "redirect:/myLoans";
            }else {
                model.addAttribute("execptionMessage", "String");
               log.info("To much");
            }
        }
        return "loanwelcome";
    }
    @GetMapping("/myLoans/{id}")
    public String uodateLoan(@PathVariable("id") Long id,  Model model){
        LoanApplay loanApplay = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        model.addAttribute("loanexist",(int)(loanApplay.getAmount()*1.5));
        return "myLoans";
    }
    public static String getClientIpAddress(HttpServletRequest request) {
        int count = 0;
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
