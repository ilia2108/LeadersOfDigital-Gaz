package io.quarkus.gazprombank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Entity
public class Client extends PanacheEntity {
    @NotNull
    public Double age;

    public String gender_code;
    public String directory;
    public Double aMRG_eop;
    public Double aCSH_eop;
    public Double aCRD_eop;
    public Double pCUR_eop;
    public Double pCRD_eop;
    public Double pSAV_eop;
    public Double pDEP_eop;
    public Double sWork_S;
    public Double tPOS_S;

    @Override
    public String toString(){
        return age.toString() + ": " + gender_code;
    }
}
