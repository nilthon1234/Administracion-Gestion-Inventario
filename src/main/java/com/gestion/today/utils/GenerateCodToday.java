package com.gestion.today.utils;

import com.gestion.today.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenerateCodToday {

    @Autowired
    private RepositoryBaby repositoryBaby;
    @Autowired
    private RepositoryChild repositoryChild;
    @Autowired
    private RepositoryLittleGirl repositoryLittleGirl;
    @Autowired
    private RepositoryMan repositoryMan;
    @Autowired
    private RepositoryWomen  repositoryWomen;

    public String generateNextCodToday(String brand) {

        Optional<String> maxBaby = repositoryBaby.findMaxCodTodayByBrand(brand);
        Optional<String> maxChild = repositoryChild.findMaxCodTodayByBrand(brand);
        Optional<String> maxLittleGirl = repositoryLittleGirl.findMaxCodTodayByBrand(brand);
        Optional<String> maxMan = repositoryMan.findMaxCodTodayByBrand(brand);
        Optional<String> maxWomen = repositoryWomen.findMaxCodTodayByBrand(brand);
        int maxNumber = 400;
        for (Optional<String> codToday : new Optional[]{maxBaby,maxChild,maxLittleGirl,maxMan,maxWomen}) {
            if (codToday.isPresent()) {
                int number = Integer.parseInt(codToday.get().replaceAll("\\D", ""));
                maxNumber = Math.max(maxNumber, number);

            }
        }

        return brand.substring(0,1).toUpperCase()+ (maxNumber + 1);
    }
}
