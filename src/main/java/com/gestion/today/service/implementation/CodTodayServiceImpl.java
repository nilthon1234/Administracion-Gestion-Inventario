package com.gestion.today.service.implementation;

import com.gestion.today.persistence.models.*;
import com.gestion.today.persistence.repository.*;
import com.gestion.today.presentation.dto.SlipperDTO;
import com.gestion.today.service.interfaces.CodTodayService;
import com.gestion.today.service.mapper.SlipperMapper;
import com.gestion.today.utils.GenerateCodToday;
import com.gestion.today.utils.GenerateQrCodeService;
import com.gestion.today.utils.SlipperFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


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
    @Autowired
    private SlipperFile slipperFile;

    @Transactional
    @Override
    public String saveCodToday(String tableName, String brand, String company, MultipartFile file)throws IOException {

        String nextCodToday = generateCodToday.generateNextCodToday(brand);
        String imagePath = null;
        File temFile = null;

        try {


            switch (tableName.toLowerCase()) {
                case "baby":
                    if (repositoryBaby.existsByCompany(company)){
                        throw new RuntimeException("Company already exists");
                    }
                    imagePath = slipperFile.uploadImage(file,nextCodToday);
                    temFile = new File(imagePath);

                    Baby baby = new Baby();
                    baby.setBrand(brand);
                    baby.setCodToday(nextCodToday);
                    baby.setCompany(company);
                    baby.setImage(imagePath);
                    State idState = new State();
                    idState.setId(1);
                    baby.setState(idState);
                    repositoryBaby.save(baby);
                    break;
                case "child":
                    if (repositoryChild.existsByCompany(company)){
                        throw new RuntimeException("Company already exists");
                    }
                    imagePath = slipperFile.uploadImage(file,nextCodToday);
                    temFile  = new File(imagePath);

                    Child child = new Child();
                    child.setBrand(brand);
                    child.setCodToday(nextCodToday);
                    child.setCompany(company);
                    child.setImage(imagePath);
                    State idStatechild = new State();
                    idStatechild.setId(1);
                    child.setState(idStatechild);
                    repositoryChild.save(child);
                    break;
                case "littleGirl":

                    if (repositoryLittleGirl.existsByCompany(company)){
                        throw new RuntimeException("Company already exists");
                    }
                    imagePath = slipperFile.uploadImage(file,nextCodToday);
                    temFile = new File(imagePath);
                    LittleGirl littleGirl = new LittleGirl();
                    littleGirl.setBrand(brand);
                    littleGirl.setCodToday(nextCodToday);
                    littleGirl.setCompany(company);

                    State idStatelittleGirl = new State();
                    idStatelittleGirl.setId(1);
                    littleGirl.setState(idStatelittleGirl);

                    littleGirl.setImage(imagePath);
                    repositoryLittleGirl.save(littleGirl);
                    break;
                case "man":
                    if (repositoryMan.existsByCompany(company)){
                        throw new RuntimeException("Company already exists");
                    }
                    imagePath = slipperFile.uploadImage(file,nextCodToday);
                    temFile = new File(imagePath);
                    Man man = new Man();
                    man.setBrand(brand);
                    man.setCodToday(nextCodToday);
                    man.setCompany(company);
                    man.setImage(imagePath);
                    State idMan = new State();
                    idMan.setId(1);
                    man.setState(idMan);
                    repositoryMan.save(man);
                    break;
                case "women":
                    if (repositoryWomen.existsByCompany(company)){
                        throw new RuntimeException("Company already exists");
                    }
                    imagePath = slipperFile.uploadImage(file,nextCodToday);
                    temFile = new File(imagePath);
                    Women women = new Women();
                    women.setBrand(brand);
                    women.setCodToday(nextCodToday);
                    women.setCompany(company);
                    State idwomenState = new State();
                    idwomenState.setId(1);
                    women.setState(idwomenState);
                    women.setImage(imagePath);
                    repositoryWomen.save(women);
                    break;
                default:
                    return "Invalid table";


            }
            String qrPath = generateQrCodeService.generateQRCode(tableName, brand,nextCodToday, company );

            return "code inserted correctly in the " + tableName +
                    " con cod_today: " + nextCodToday +
                    ".QR generate in: " + qrPath;

        } catch (Exception e) {
            if (temFile != null && temFile.exists()) {
                temFile.delete();
            }
            throw e;
        }
    }

    @Override
    public Optional<SlipperDTO> detailsShoe(SlipperType slipperType, String brand, String codToday) {
        return switch (slipperType) {
            case BABY -> repositoryBaby.findByBrandAndCodToday(brand, codToday)
                    .map(SlipperMapper::mapToDto);
            case CHILD -> repositoryChild.findByBrandAndCodToday(brand, codToday)
                    .map(SlipperMapper::mapToDto);
            case LITTLEGIRL -> repositoryLittleGirl.findByBrandAndCodToday(brand, codToday)
                    .map(SlipperMapper::mapToDto);
            case MAN -> repositoryMan.findByBrandAndCodToday(brand, codToday)
                    .map(SlipperMapper::mapToDto);
            case WOMEN -> repositoryWomen.findByBrandAndCodToday(brand, codToday)
                    .map(SlipperMapper::mapToDto);
            default -> Optional.empty();
        };

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
