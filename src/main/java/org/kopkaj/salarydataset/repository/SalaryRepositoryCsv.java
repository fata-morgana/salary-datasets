package org.kopkaj.salarydataset.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.model.SalaryDatasetRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SalaryRepositoryCsv implements SalaryRepository {
    private static final Logger logger = LoggerFactory.getLogger(SalaryRepositoryCsv.class);
    final List<SalaryDataset> datasets;
    public  SalaryRepositoryCsv(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        File file = null;
        if (resource != null) {
            try {
                file = new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            assert file != null;
            try (FileReader fr = new FileReader(file)) {
                List<SalaryDatasetRaw> raw = new CsvToBeanBuilder(fr).
                        withType(SalaryDatasetRaw.class).
                        build().
                        parse();
                datasets = raw.stream().
                        filter(entry -> !"".equalsIgnoreCase(entry.getTimestamp())).
                        map(entry -> new SalaryDataset(entry)).
                        collect(Collectors.toList());
            }
        } catch (FileNotFoundException e) {
            logger.error(String.format("Unable to find file %s.", fileName), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(String.format("IOException error on file %s.", fileName), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SalaryDataset> getAll() {
        return datasets;
    }
}