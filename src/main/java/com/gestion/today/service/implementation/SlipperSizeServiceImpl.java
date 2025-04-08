package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.*;
import com.gestion.today.persistence.repository.*;
import com.gestion.today.service.interfaces.HasImage;
import com.gestion.today.service.interfaces.SizeService;
import com.gestion.today.utils.SlipperFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class SlipperSizeServiceImpl {

    private final Map<String, SizeService<?>> sizeServiceMap;
    private final SlipperFile slipperFile;
    private final RepositoryBaby repositoryBaby;
    private final RepositoryChild repositoryChild;
    private final RepositoryLittleGirl repositoryLittleGirl;
    private final RepositoryMan repositoryMan;
    private final RepositoryWomen repositoryWomen;

    @Autowired
    public SlipperSizeServiceImpl(List<SizeService<?>> updaters, SlipperFile slipperFile, RepositoryBaby repositoryBaby,
                                  RepositoryChild repositoryChild, RepositoryLittleGirl repositoryLittleGirl,
                                  RepositoryMan repositoryMan, RepositoryWomen repositoryWomen){
        this.sizeServiceMap = updaters.stream()
                .collect(Collectors.toMap(SizeService::getEntityType, Function.identity()));
        this.slipperFile = slipperFile;
        this.repositoryBaby = repositoryBaby;
        this.repositoryChild = repositoryChild;
        this.repositoryLittleGirl = repositoryLittleGirl;
        this.repositoryMan = repositoryMan;
        this.repositoryWomen = repositoryWomen;
    }

    public Object updateSizes(String tableType, String brand, String codToday, Map<String,Integer> sizes){
        SizeService<?> updater = sizeServiceMap.get(tableType.toLowerCase());
        if (updater == null){
            throw new RuntimeException("Unknown table type: " + tableType);
        }
        return  updater.updateSizes(codToday, sizes);
    }

    public String updateNameFile (String tableType, String brand, String codToday, MultipartFile file) throws IOException{
        if (file == null || file.isEmpty()){
            throw new IllegalArgumentException("the provided file is invalid");
        }
        Map<String, JpaRepository<? extends HasImage, ?>> repositories = Map.of(
                "baby", repositoryBaby,
                "child", repositoryChild,
                "littleGirl", repositoryLittleGirl,
                "man", repositoryMan,
                "women", repositoryWomen
        );
        JpaRepository<? extends  HasImage, ?> repository = repositories.get(tableType.toLowerCase());
        if (repository == null){
            throw new RuntimeException("Unknown table type: " + tableType);
        }
        //buscar the entity
        HasImage entity ;
        if (repository instanceof  RepositoryBaby) {
            entity = ((RepositoryBaby) repository).findByBrandAndCodToday(brand, codToday)
                    .orElseThrow(() -> new RuntimeException("Record not found in table:'" + tableType + "'"));

        } else if (repository instanceof RepositoryChild ) {
            entity = ((RepositoryChild) repository).findByBrandAndCodToday(brand, codToday)
                    .orElseThrow(()-> new RuntimeException("Record not found in table: '" + tableType + "'"));

        } else if (repository instanceof RepositoryLittleGirl ) {
            entity = ((RepositoryLittleGirl) repository).findByBrandAndCodToday(brand, codToday)
                    .orElseThrow(()-> new RuntimeException("Record not found in table: '" + tableType + "'"));

        }else if (repository instanceof RepositoryMan) {
            entity = ((RepositoryMan) repository).findByBrandAndCodToday(brand, codToday)
                    .orElseThrow(()-> new RuntimeException("Record not found in table: '" + tableType + "'"));

        }else if (repository instanceof RepositoryWomen ) {
            entity = ((RepositoryWomen) repository).findByBrandAndCodToday(brand, codToday)
                    .orElseThrow(()-> new RuntimeException("Record not found in table: '" + tableType + "'"));

        }else {
            throw new RuntimeException("Unknown table type: " + tableType);
        }
        String newFileName = slipperFile.updateImage(file, entity.getImage(), codToday);
        entity.setImage(newFileName);

        if (repository instanceof RepositoryBaby){
            ((RepositoryBaby) repository).save((Baby)entity);

        } else if (repository instanceof RepositoryChild) {
            ((RepositoryChild) repository).save((Child) entity);

        } else if (repository instanceof RepositoryLittleGirl) {
            ((RepositoryLittleGirl) repository).save((LittleGirl) entity);

        } else if (repository instanceof RepositoryMan) {
            ((RepositoryMan) repository).save((Man) entity);

        } else if (repository instanceof RepositoryWomen) {
            ((RepositoryWomen) repository).save((Women) entity);

        }


        return "Imagen actualizada correctamente para la tabla: " + tableType + ", codToday: " + codToday;

    }

    public boolean  discountStockBySize(String codToday, Double size, int amount){
        Optional<Baby> babyOpt = repositoryBaby.findByCodToday(codToday);
        if (babyOpt.isPresent()){
            return discountSize(babyOpt.get(), size, amount);
        }
        Optional<Child> childOpt = repositoryChild.findByCodToday(codToday);
        if (childOpt.isPresent()){
            return discountSize(childOpt.get(),size,amount);
        }
        Optional<LittleGirl> littleGirldOpt = repositoryLittleGirl.findByCodToday(codToday);
        if (littleGirldOpt.isPresent()){
            return discountSize(littleGirldOpt.get(),size,amount);
        }
        Optional<Man> manOpt = repositoryMan.findByCodToday(codToday);
        if (manOpt.isPresent()){
            return discountSize(manOpt.get(),size,amount);
        }
        Optional<Women> womendOpt = repositoryWomen.findByCodToday(codToday);
        if (womendOpt.isPresent()){
            return discountSize(womendOpt.get(),size,amount);
        }
        return false;

    }

    private boolean discountSize(Object slipper, Double size, int amount) {

        try {

                String prefix;

                // Determinar el prefijo adecuado según el tipo de objeto
                if (slipper instanceof Women || slipper instanceof Man) {
                    prefix = "usa";
                }
                else if (slipper instanceof Baby || slipper instanceof Child || slipper instanceof  LittleGirl) {
                    prefix = "eu";
                } else {
                    throw new RuntimeException("Unknown slipper type: no prefix defined");
                }

            String fieldName = prefix + (size % 1 == 0 ? String.valueOf(size.intValue()) : String.valueOf(size)
                    .replace(".","_"));

            Field field = slipper.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            int actual = (int) field.get(slipper);
            if (actual < amount) return false;
            field.set(slipper, actual - amount);
            //descontar el amount general
            Field amountField = slipper.getClass().getDeclaredField("amount");
            amountField.setAccessible(true);
            int currentAmount = (int) amountField.get(slipper);
            amountField.set(slipper, currentAmount - amount);
            saveSlipper(slipper);
            return true;

        }catch (NoSuchFieldException e){
            throw new RuntimeException("Size field not found: " + e.getMessage());
        }catch (IllegalAccessException e){
            throw new RuntimeException("the field could not be accessed: " + e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("Error updating size" + e.getMessage());
        }
    }

    private void saveSlipper(Object slipper) {

        if (slipper instanceof Baby){
            repositoryBaby.save((Baby) slipper);
        } else if (slipper instanceof Child) {
            repositoryChild.save((Child) slipper);
        } else if (slipper instanceof LittleGirl) {
            repositoryLittleGirl.save((LittleGirl) slipper);
        } else if (slipper instanceof Man) {
            repositoryMan.save((Man) slipper);
        } else if (slipper instanceof Women) {
            repositoryWomen.save((Women) slipper);
        }
    }


}