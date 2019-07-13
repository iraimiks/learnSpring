package lv.raimondstest.loansystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class LoanApplay {
    @Id
    @GeneratedValue
    Long id;
    int amount;
    LocalDateTime term;
    public LoanApplay(){}

    public LoanApplay(int amount) {
        this.amount = amount;
        this.term = LocalDateTime.now();
    }
}
