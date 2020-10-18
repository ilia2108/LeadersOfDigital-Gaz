package io.quarkus.gazprombank;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import io.quarkus.gazprombank.Client;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

@ApplicationScoped
@Transactional(REQUIRED)
public class ClientService {
    @Transactional(SUPPORTS)
    public List<Client> findAllClients(){
        return Client.listAll();
    }

    @Transactional(SUPPORTS)
    public Client findClientById(Long id){
        return Client.findById(id);
    }

    public Client persistClient(@Valid Client client){
        Client.persist(client);
        return client;
    }

    public Client updateClient(@Valid Client client){
        Client entity = Client.findById(client.id);
        entity.aCRD_eop = client.aCRD_eop;
        entity.aCSH_eop = client.aCSH_eop;
        entity.aMRG_eop = client.aMRG_eop;
        entity.age = client.age;
        entity.directory = client.directory;
        entity.gender_code = client.gender_code;
        entity.pCRD_eop = client.pCRD_eop;
        entity.pCUR_eop = client.pCUR_eop;
        entity.pDEP_eop = client.pDEP_eop;
        entity.pSAV_eop = client.pSAV_eop;
        entity.sWork_S = client.sWork_S;
        entity.tPOS_S = client.tPOS_S;

        return entity;
    }

    public void deleteClient(Long id){
        Client client = Client.findById(id);
        client.delete();
    }
}
