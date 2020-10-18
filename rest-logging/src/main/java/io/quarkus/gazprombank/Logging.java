package io.quarkus.gazprombank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import java.util.Random;

@Entity
public class Logging extends PanacheEntity {
    @NotNull
    public Long userID;

    @NotNull
    public String msg;
    

    @Override
    public String toString(){
        return "User #" + userID.toString()
            + ": " + msg;
    }

    public static Logging findRandom(){
        long countLog = Logging.count();
        Random random = new Random();
        int randomLog = random.nextInt((int) countLog);
        return Logging.findAll().page(randomLog, 1).firstResult();
    }
}
