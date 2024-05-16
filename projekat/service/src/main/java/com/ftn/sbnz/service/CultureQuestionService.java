package com.ftn.sbnz.service;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import com.ftn.sbnz.dto.CultureAnswerDTO;
import com.ftn.sbnz.dto.CultureQuestionRequestDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CultureQuestionService {

    public CultureAnswerDTO processCultureQuestion(CultureQuestionRequestDTO requestDTO) {
        String culture = requestDTO.getCulture();
        String plantingInstructions = getPlantingInstructionsFromKnowledgeSource(culture);
        LocalDate bestPlantingDate = getBestPlantingDateFromKnowledgeSource(culture);
        LocalDate currentDate = LocalDate.now();
        boolean isRightTime = isRightTimeToPlant(currentDate, bestPlantingDate);
        return new CultureAnswerDTO(plantingInstructions, isRightTime);
    }

    private String getPlantingInstructionsFromKnowledgeSource(String culture) {
        //projekat/service/src/main/java/com/ftn/sbnz/service
        try (CSVReader reader = new CSVReader(new FileReader("../../../../../../../../../resursi/CultureInfo.csv"))) {

            String[] nextRecord;
            // skip header
            try {
                reader.readNext();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }

            try {
                while ((nextRecord = reader.readNext()) != null) {
                    String cultureName = nextRecord[0];
                    String instructions = nextRecord[1];
                    if (cultureName.equalsIgnoreCase(culture)) {
                        return instructions;
                    }
                }
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //default
        return "Instructions not found for culture: " + culture;
    }

    private LocalDate getBestPlantingDateFromKnowledgeSource(String culture) {
        // Implementirajte logiku za dobijanje najboljeg datuma za sadnju iz izvora znanja
        // Na primer, možete pristupiti bazi podataka ili vanjskom servisu
        // Ovde biste trebali vratiti LocalDate objekat koji predstavlja najbolji datum za sadnju
        return LocalDate.now(); // Dummy implementacija, vraća trenutni datum
    }

    private boolean isRightTimeToPlant(LocalDate currentDate, LocalDate bestPlantingDate) {
        // Implementirajte logiku za upoređivanje trenutnog datuma sa najboljim datumom za sadnju
        // Na primer, možete proveriti da li je trenutni datum blizu najboljeg datuma za sadnju
        return currentDate.isEqual(bestPlantingDate) || currentDate.isBefore(bestPlantingDate);
    }
}
