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




}