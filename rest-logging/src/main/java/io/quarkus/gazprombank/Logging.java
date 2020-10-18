package io.quarkus.gazprombank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;

@Entity
public class Logging extends PanacheEntity {
    @NotNull
    public Long userID;

    @NotNull
    public String msg;
    
    @NotNull
    public Date tmstmp;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public String toString(){
        return sdf.format(tmstmp) + ": " + "User #" + userID.toString()
            + " - " + msg;
    }

    public static Logging findRandom(){
        long countLog = Logging.count();
        Random random = new Random();
        int randomLog = random.nextInt((int) countLog);
        return Logging.findAll().page(randomLog, 1).firstResult();
    }
}
