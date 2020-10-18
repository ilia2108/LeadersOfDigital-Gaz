package io.quarkus.gazprombank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class MoneyTransaction extends PanacheEntity{
    @NotNull
    public Long client_id;

    public String TRANSACTION_DT;
    public String MCC_KIND_CD;
    public double MCC_CD;
    public double CARD_AMOUNT_EQV_CBR;

    @Override
    public String toString(){
        return client_id.toString();
    }

}
