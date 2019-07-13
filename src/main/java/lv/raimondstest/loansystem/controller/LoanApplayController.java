package lv.raimondstest.loansystem.controller;


import lv.raimondstest.loansystem.config.LoadDatabes;
import lv.raimondstest.loansystem.model.LoanApplay;
import lv.raimondstest.loansystem.repo.LoanApplayRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LoanApplayController {
    private final LoanApplayRepo repo;
    private static List<LoanApplay> loans = new ArrayList<>();
    static {
        loans.add(new LoanApplay(30));
    }
    public LoanApplayController(LoanApplayRepo repo) {
        this.repo = repo;
    }

//    @GetMapping("/myLoans")
//    private List<LoanApplay> all(){
//
//        return repo.findAll();
//    }
    @GetMapping("/myLoans")
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
    public String saveNewLoan(Model model, @ModelAttribute("loanAdd") LoanApplay loanApplay){
        int loan = loanApplay.getAmount();
        String loanInstring = Integer.toString(loan);
        LocalDateTime date = LocalDateTime.now();
        if(loanInstring != null && date != null){
            LoanApplay newLoan = new LoanApplay(loan);
            loans.add(newLoan);
            return "redirect:/myLoans";
        }
        return "loanwelcome";
    }
    @PutMapping("/myLoans/{id}")
    private LoanApplay loanChange(@RequestBody LoanApplay existLoan, @PathVariable Long id){
        return repo.findById(id)
                .map(loanApplay ->{
                    loanApplay.setAmount(existLoan.getAmount());
                    loanApplay.setTerm(existLoan.getTerm());
                    return repo.save(loanApplay);
                }).orElseGet(()-> {
                            existLoan.setId(id);
                            return repo.save(existLoan);
                        });
    }
    @DeleteMapping("/myLoans/{id}")
    void deleteLoanAfter(@PathVariable Long id){
        repo.deleteById(id);
    }
}
