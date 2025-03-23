package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Women;
import com.gestion.today.persistence.repository.RepositoryWomen;
import com.gestion.today.service.exceptiones.InvalidSizeException;
import com.gestion.today.service.interfaces.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@RequiredArgsConstructor
@Service
public class WomenSizeServiceImpl implements SizeService<Women> {

    private final RepositoryWomen repositoryWomen;
    @Override
    public String getEntityType() {
        return "women";
    }

    @Override
    public Women updateSizes(String codToday, Map<String, Integer> sizes) {
        Women women = repositoryWomen.findByCodToday(codToday)
                    .orElseThrow(() -> new EntityNotFoundException("No Women slipper fount with cosToday: " + codToday));

        updateWomenSizes(women, sizes);
        repositoryWomen.save(women);
        women.setAmount(repositoryWomen.calculateTotalAmount(codToday));


        return repositoryWomen.save(women);
    }

    private void updateWomenSizes(Women women, Map<String, Integer> sizes) {
        sizes.forEach((size, amount) -> {
            switch (size) {
                case "usa5_5":women.setUsa5_5(women.getUsa5_5() + amount);break;
                case "usa6":women.setUsa6(women.getUsa6() + amount);break;
                case "usa6_5":women.setUsa6_5(women.getUsa6_5() + amount);break;
                case "usa7":women.setUsa7(women.getUsa7() + amount);break;
                case "usa7_5":women.setUsa7_5(women.getUsa7_5() + amount);break;
                case "usa8":women.setUsa8(women.getUsa8() + amount);break;
                case "usa8_5":women.setUsa8_5(women.getUsa8_5() + amount);break;
                case "usa9":women.setUsa9(women.getUsa9() + amount);break;
                case "usa9_5":women.setUsa9_5(women.getUsa9_5() + amount);break;
                case "usa10":women.setUsa10(women.getUsa10() + amount);break;
                case "usa10_5":women.setUsa10_5(women.getUsa10_5() + amount);break;
                case "usa11":women.setUsa11(women.getUsa11() + amount);break;
                case "usa11_5":women.setUsa11_5(women.getUsa11_5() + amount);break;
                case "usa12":women.setUsa12(women.getUsa12() + amount);break;
                default: throw  new InvalidSizeException("Size unknown to man: " + size);
            }
        });
    }
}
