package lv.raimondstest.loansystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

@Data
@Entity
public class LoanApplay {
    @Id
    @GeneratedValue
    Long id;
    int amount;
    String term;
    int backAmount;

    public LoanApplay(){}

    public LoanApplay(int amount, String term) {

        this.amount = amount;
        this.term = term;
        this.backAmount = (int)(amount*1.1);
    }
}
