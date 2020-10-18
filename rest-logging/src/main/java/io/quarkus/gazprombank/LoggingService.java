package io.quarkus.gazprombank;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

@ApplicationScoped
@Transactional(REQUIRED)
public class LoggingService {
    
    @Transactional(SUPPORTS)
    public List<Logging> findAllLogs(){
        return Logging.listAll();
    }

    @Transactional(SUPPORTS)
    public Logging findLogById(Long id){
        return Logging.findLogById(id);
    }

    @Transactional(SUPPORTS)
    public Logging findRandom(){
        Logging log = null;
        while(log == null){
            log = Logging.findRandom();
        }
        return log;
    }

    public Logging persistLog(@Valid Logging log){
        Logging.persist(log);
        return log;
    }

    public Logging updateLog(@Valid Logging log){
        Logging entity = Logging.findById(log.id);
        entity.tmstmp = log.tmstmp;
        entity.msg = log.msg;
        entity.userID = log.userID;
        return entity;
    }

    public void deleteLog(Long id){
        Logging log = Logging.findById(id);
        log.delete();
    }
}
