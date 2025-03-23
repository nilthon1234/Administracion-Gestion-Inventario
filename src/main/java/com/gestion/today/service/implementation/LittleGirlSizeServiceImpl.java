package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.LittleGirl;
import com.gestion.today.persistence.repository.RepositoryLittleGirl;
import com.gestion.today.service.exceptiones.InvalidSizeException;
import com.gestion.today.service.interfaces.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class LittleGirlSizeServiceImpl implements SizeService<LittleGirl> {

    private RepositoryLittleGirl repositoryLittleGirl;
    @Override
    public String getEntityType() {
        return "littleGirl";
    }

    @Override
    public LittleGirl updateSizes(String codToday, Map<String, Integer> sizes) {
        LittleGirl littleGirl = repositoryLittleGirl.findByCodToday(codToday)
                .orElseThrow(() -> new EntityNotFoundException("No LittleGirl slipper fount with codToday: " + codToday));
        updateLittleSize(littleGirl, sizes);
        repositoryLittleGirl.save(littleGirl);
        littleGirl.setAmount(repositoryLittleGirl.calculateTotalAmount(codToday));

        return repositoryLittleGirl.save(littleGirl);
    }

    private void updateLittleSize(LittleGirl littleGirl, Map<String, Integer> sizes) {
        sizes.forEach((size, amount)-> {
            switch (size) {
                case "eu28":littleGirl.setEu28(littleGirl.getEu28() + amount);break;
                case "eu28_5":littleGirl.setEu28_5(littleGirl.getEu28_5() + amount);break;
                case "eu29":littleGirl.setEu29(littleGirl.getEu29() + amount);break;
                case "eu29_5":littleGirl.setEu29_5(littleGirl.getEu29_5() + amount);break;
                case "eu30":littleGirl.setEu30(littleGirl.getEu30() + amount);break;
                case "eu30_5":littleGirl.setEu30_5(littleGirl.getEu30_5() + amount);break;
                case "eu31":littleGirl.setEu31(littleGirl.getEu31() + amount);break;
                case "eu31_5":littleGirl.setEu31_5(littleGirl.getEu31_5() + amount);break;
                case "eu32":littleGirl.setEu32(littleGirl.getEu32() + amount);break;
                case "eu32_5":littleGirl.setEu32_5(littleGirl.getEu32_5() + amount);break;
                case "eu33":littleGirl.setEu33(littleGirl.getEu33() + amount);break;
                case "eu33_5":littleGirl.setEu33_5(littleGirl.getEu33_5() + amount);break;
                case "eu34":littleGirl.setEu34(littleGirl.getEu34() + amount);break;
                case "eu34_5":littleGirl.setEu34_5(littleGirl.getEu34_5() + amount);break;
                case "eu35":littleGirl.setEu35(littleGirl.getEu35() + amount);break;
                case "eu35_5":littleGirl.setEu35_5(littleGirl.getEu35_5() + amount);break;
                case "eu36":littleGirl.setEu36(littleGirl.getEu36() + amount);break;
                case "eu36_5":littleGirl.setEu36_5(littleGirl.getEu36_5() + amount);break;
                case "eu37":littleGirl.setEu37(littleGirl.getEu37() + amount);break;
                case "eu37_5":littleGirl.setEu37_5(littleGirl.getEu37_5() + amount);break;
                case "eu38":littleGirl.setEu38(littleGirl.getEu38() + amount);break;
                case "eu38_5":littleGirl.setEu38_5(littleGirl.getEu38_5() + amount);break;
                default: throw new InvalidSizeException("Size unknown to littleGirl: " + size);

            }
        });
    }
}
