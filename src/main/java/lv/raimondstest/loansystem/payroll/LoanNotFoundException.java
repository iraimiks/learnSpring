package lv.raimondstest.loansystem.payroll;

public class LoanNotFoundException extends RuntimeException{
    LoanNotFoundException(Long id){
        super("Could not found loan by id: "+id);
    }
}
