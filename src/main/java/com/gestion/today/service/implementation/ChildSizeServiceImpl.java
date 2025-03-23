package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.Child;
import com.gestion.today.persistence.repository.RepositoryChild;
import com.gestion.today.service.exceptiones.InvalidSizeException;
import com.gestion.today.service.interfaces.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChildSizeServiceImpl implements SizeService<Child> {

    private final RepositoryChild repositoryChild;
    @Override
    public String getEntityType() {
        return "child";
    }

    @Override
    public Child updateSizes(String codToday, Map<String, Integer> sizes) {

        Child child = repositoryChild.findByCodToday(codToday)
                .orElseThrow(()-> new EntityNotFoundException("No child slipper fount with codToday: " + codToday));

        updateChildSize(child, sizes);
        repositoryChild.save(child);
        child.setAmount(repositoryChild.calculateTotalAmount(codToday));

        return repositoryChild.save(child);
    }

    private void updateChildSize(Child child, Map<String, Integer> sizes){
        sizes.forEach((size, amount) ->{
            switch (size) {
                case "eu28":child.setEu28(child.getEu28() + amount);break;
                case "eu28_5":child.setEu28_5(child.getEu28_5() + amount);break;
                case "eu29":child.setEu29(child.getEu29() + amount);break;
                case "eu29_5":child.setEu29_5(child.getEu29_5() + amount);break;
                case "eu30":child.setEu30(child.getEu30() + amount);break;
                case "eu30_5":child.setEu30_5(child.getEu30_5() + amount);break;
                case "eu31":child.setEu31(child.getEu31() + amount);break;
                case "eu31_5":child.setEu31_5(child.getEu31_5() + amount);break;
                case "eu32":child.setEu32(child.getEu32() + amount);break;
                case "eu32_5":child.setEu32_5(child.getEu32_5() + amount);break;
                case "eu33":child.setEu33(child.getEu33() + amount);break;
                case "eu33_5":child.setEu33_5(child.getEu33_5() + amount);break;
                case "eu34":child.setEu34(child.getEu34() + amount);break;
                case "eu34_5":child.setEu34_5(child.getEu34_5() + amount);break;
                case "eu35":child.setEu35(child.getEu35() + amount);break;
                case "eu35_5":child.setEu35_5(child.getEu35_5() + amount);break;
                case "eu36":child.setEu36(child.getEu36() + amount);break;
                case "eu36_5":child.setEu36_5(child.getEu36_5() + amount);break;
                case "eu37":child.setEu37(child.getEu37() + amount);break;
                case "eu37_5":child.setEu37_5(child.getEu37_5() + amount);break;
                case "eu38":child.setEu38(child.getEu38() + amount);break;
                case "eu38_5":child.setEu38_5(child.getEu38_5() + amount);break;
                default: throw  new InvalidSizeException("Size unknown to child: " + size);
            }
        });
    }
}
