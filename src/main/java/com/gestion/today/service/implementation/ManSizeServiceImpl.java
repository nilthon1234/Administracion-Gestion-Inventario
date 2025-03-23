package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Man;
import com.gestion.today.persistence.repository.RepositoryMan;
import com.gestion.today.service.exceptiones.InvalidSizeException;
import com.gestion.today.service.interfaces.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ManSizeServiceImpl implements SizeService<Man> {

    private  final RepositoryMan repositoryMan;
    @Override
    public String getEntityType() {
        return "man";
    }

    @Override
    public Man updateSizes(String codToday, Map<String, Integer> sizes) {
        Man man = repositoryMan.findByCodToday(codToday)
                .orElseThrow(()-> new EntityNotFoundException("No Man slipper fount with codToday: " + codToday));

        updateManSizes(man, sizes);
        repositoryMan.save(man);
        man.setAmount(repositoryMan.calculateTotalAmount(codToday));

        return repositoryMan.save(man);
    }

    private void updateManSizes(Man man, Map<String, Integer> sizes) {

        sizes.forEach((size, amount) -> {
            switch (size) {
                case "usa5_5":man.setUsa5_5(man.getUsa5_5() + amount);break;
                case "usa6":man.setUsa6(man.getUsa6() + amount);break;
                case "usa6_5":man.setUsa6_5(man.getUsa6_5() + amount);break;
                case "usa7":man.setUsa7(man.getUsa7() + amount);break;
                case "usa7_5":man.setUsa7_5(man.getUsa7_5() + amount);break;
                case "usa8":man.setUsa8(man.getUsa8() + amount);break;
                case "usa8_5":man.setUsa8_5(man.getUsa8_5() + amount);break;
                case "usa9":man.setUsa9(man.getUsa9() + amount);break;
                case "usa9_5":man.setUsa9_5(man.getUsa9_5() + amount);break;
                case "usa10":man.setUsa10(man.getUsa10() + amount);break;
                case "usa10_5":man.setUsa10_5(man.getUsa10_5() + amount);break;
                case "usa11":man.setUsa11(man.getUsa11() + amount);break;
                case "usa11_5":man.setUsa11_5(man.getUsa11_5() + amount);break;
                case "usa12":man.setUsa12(man.getUsa12() + amount);break;
                default: throw new InvalidSizeException("Size unknown to man: " + size);
            }
        });
    }
}
