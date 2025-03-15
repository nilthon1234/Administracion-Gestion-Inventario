package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Baby;
import com.gestion.today.persistence.repository.RepositoryBaby;
import com.gestion.today.service.interfaces.ServiceBaby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BabyServiceImpl implements ServiceBaby {

    private final RepositoryBaby repositoryBaby;

    @Override
    public Baby saveBaby(Baby baby) {
        return repositoryBaby.save(baby);
    }

    @Override
    public Baby updateZise(String codToday, Map<String, Integer> zises) {

        Optional<Baby> optionalBaby = repositoryBaby.findByCodToday(codToday);
        if (optionalBaby.isEmpty()){
            throw  new RuntimeException("No se encontro si producto con codToday: " + codToday);
        }

        Baby baby = optionalBaby.get();

        zises.forEach((zise, amount)->{
            switch (zise){
                case "eu18": baby.setEu18(baby.getEu18() + amount); break;
                case "eu18_5": baby.setEu18_5(baby.getEu18_5() + amount); break;
                case "eu19": baby.setEu19(baby.getEu19() + amount); break;
                case "eu19_5": baby.setEu19_5(baby.getEu19_5() + amount); break;
                case "eu20": baby.setEu20(baby.getEu20() + amount); break;
                case "eu20_5": baby.setEu20_5(baby.getEu20_5() + amount); break;
                case "eu21": baby.setEu21(baby.getEu21() + amount); break;
                case "eu21_5": baby.setEu21_5(baby.getEu21_5() + amount); break;
                case "eu22": baby.setEu22(baby.getEu22() + amount); break;
                case "eu22_5": baby.setEu22_5(baby.getEu22_5() + amount); break;
                case "eu23": baby.setEu23(baby.getEu23() + amount); break;
                case "eu23_5": baby.setEu23_5(baby.getEu23_5() + amount); break;
                case "eu24": baby.setEu24(baby.getEu24() + amount); break;
                case "eu24_5": baby.setEu24_5(baby.getEu24_5() + amount); break;
                case "eu25": baby.setEu25(baby.getEu25() + amount); break;
                case "eu25_5": baby.setEu25_5(baby.getEu25_5() + amount); break;
                case "eu26": baby.setEu26(baby.getEu26() + amount); break;
                case "eu26_5": baby.setEu26_5(baby.getEu26_5() + amount); break;
                case "eu27": baby.setEu27(baby.getEu27() + amount); break;
                case "eu27_5": baby.setEu27_5(baby.getEu27_5() + amount); break;
            }
        });
        repositoryBaby.save(baby);
        baby.setAmount(repositoryBaby.calculateTotalAmount(codToday));

        return repositoryBaby.save(baby);
    }
}
