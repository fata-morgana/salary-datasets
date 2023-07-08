package org.kopkaj.salarydataset.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.model.SalaryDatasetRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaryRepositoryJson implements SalaryRepository {
    private final Logger logger = LoggerFactory.getLogger(SalaryRepositoryJson.class);
    final List<SalaryDataset> datasets;

    public SalaryRepositoryJson(String fileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<SalaryDatasetRaw> raw = objectMapper.readValue(
                    getClass().getClassLoader().getResourceAsStream(fileName),
                    new TypeReference<List<SalaryDatasetRaw>>(){});
            datasets = raw.stream().
                    filter(entry -> !"".equalsIgnoreCase(entry.getTimestamp())).
                    map(entry -> new SalaryDataset(entry)).
                    collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(String.format("Unable to parse JSON from file: %s", fileName), e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<SalaryDataset> getAll() {
        return datasets;
    }
}
