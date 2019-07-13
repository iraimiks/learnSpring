package lv.raimondstest.loansystem.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lv.raimondstest.loansystem.model.LoanApplay;
import lv.raimondstest.loansystem.repo.LoanApplayRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@Slf4j
public class LoadDatabes {
    //LocalDateTime today =  LocalDateTime.now();

//    @Bean
//    CommandLineRunner initDatabase(LoanApplayRepo repo){
//        return args -> {
//            log.info("Preloading " + repo.save(new LoanApplay(30,new Date())));
//            log.info("Preloading " + repo.save(new LoanApplay(3330,new Date())));
//
//        };
//    }

}
