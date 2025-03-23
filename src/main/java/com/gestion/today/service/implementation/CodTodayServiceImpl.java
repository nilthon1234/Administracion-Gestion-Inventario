package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.*;
import com.gestion.today.persistence.repository.*;
import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.CodTodayService;
import com.gestion.today.service.mapper.SlipperMapper;
import com.gestion.today.utils.GenerateCodToday;
import com.gestion.today.utils.GenerateQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CodTodayServiceImpl implements CodTodayService {

    @Autowired
    private RepositoryBaby repositoryBaby;
    @Autowired
    private RepositoryChild repositoryChild;
    @Autowired

    private RepositoryLittleGirl repositoryLittleGirl;
    @Autowired
    private RepositoryMan repositoryMan;
    @Autowired
    private RepositoryWomen repositoryWomen;

    @Autowired
    private GenerateCodToday generateCodToday;

    @Autowired
    private GenerateQrCodeService generateQrCodeService;


    @Override
    public String saveCodToday(String tableName, String brand, String company) {

        String nextCodToday = generateCodToday.generateNextCodToday(brand);

        switch (tableName.toLowerCase()) {
            case "baby":
                Baby baby = new Baby();
                baby.setBrand(brand);
                baby.setCodToday(nextCodToday);
                baby.setCompany(company);
                repositoryBaby.save(baby);
                break;
            case "child":
                Child child = new Child();
                child.setBrand(brand);
                child.setCodToday(nextCodToday);
                child.setCompany(company);
                repositoryChild.save(child);
                break;
            case "littleGirl":
                LittleGirl littleGirl = new LittleGirl();
                littleGirl.setBrand(brand);
                littleGirl.setCodToday(nextCodToday);
                littleGirl.setCompany(company);
                repositoryLittleGirl.save(littleGirl);
                break;
            case "man":
                Man man = new Man();
                man.setBrand(brand);
                man.setCodToday(nextCodToday);
                man.setCompany(company);
                repositoryMan.save(man);
                break;
            case "women":
                Women women = new Women();
                women.setBrand(brand);
                women.setCodToday(nextCodToday);
                women.setCompany(company);
                repositoryWomen.save(women);
                break;
            default:
                return "Invalid table";


        }
        String qrPath = generateQrCodeService.generateQRCode(tableName, brand,nextCodToday, company );

        return "code inserted correctly in the " + tableName +
                " con cod_today: " + nextCodToday +
                ".QR generate in: " + qrPath;
    }

    @Override
    public Optional<SlipperDTO> detailsShoe(SlipperType slipperType, String brand, String codToday) {
        switch (slipperType){
            case BABY:
                return repositoryBaby.findByBrandAndCodToday(brand, codToday)
                        .map(SlipperMapper::mapToDto);
            case CHILD:
                return repositoryChild.findByBrandAndCodToday(brand, codToday)
                        .map(SlipperMapper::mapToDto);
            case LITTLEGIRL:
                return repositoryLittleGirl.findByBrandAndCodToday(brand, codToday)
                        .map(SlipperMapper::mapToDto);
            case MAN:
                return repositoryMan.findByBrandAndCodToday(brand, codToday)
                        .map(SlipperMapper::mapToDto);
            case WOMEN:
                return  repositoryWomen.findByBrandAndCodToday(brand, codToday)
                        .map(SlipperMapper::mapToDto);
            default:
                return Optional.empty();
        }

    }

    @Override
    public String updateQR(String codToday, String tableName, String brand, String company) {

            boolean found = false;

        switch (tableName.toLowerCase()){
            case "baby":
                found = repositoryBaby.findByCodToday(codToday).isPresent();
                break;
            case  "child":
                found = repositoryChild.findByCodToday(codToday).isPresent();
                break;
            case "littleGirl":
                found = repositoryLittleGirl.findByCodToday(codToday).isPresent();
                break;
            case "man":
                found = repositoryMan.findByCodToday(codToday).isPresent();
                break;
            case "women":
                found = repositoryWomen.findByCodToday(codToday).isPresent();
                break;
            default:
                return "table invalid";
        }
        if (!found){
            return "codToday: " + codToday + " was not found in the table: " + tableName;
        }
        /*
        TENER EN CUENTA EN ESPISIFICAR EL ORDEN DE LOS PARAMTROS PARA MOSTRAR LOS
        DETALLES DE DICHA ZAPATILLA*/
        return generateQrCodeService.generateQRCode( tableName, brand,codToday, company);
    }


}
