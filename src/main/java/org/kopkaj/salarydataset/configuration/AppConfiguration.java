package org.kopkaj.salarydataset.configuration;

import org.kopkaj.salarydataset.controller.SalaryController;
import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.kopkaj.salarydataset.repository.SalaryRepositoryCsv;
import org.kopkaj.salarydataset.repository.SalaryRepositoryJson;
import org.kopkaj.salarydataset.service.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean
    public SalaryService salaryService(@Value("${dataset.source}") String source,
                                       @Qualifier("salaryCsv") SalaryRepository csv,
                                       @Qualifier("salaryJson") SalaryRepository json) {
        if ("CSV".equalsIgnoreCase(source)) {
            return new SalaryService(csv);
        }
        else if ("JSON".equalsIgnoreCase(source)) {
            return new SalaryService(json);
        }

        throw new RuntimeException("Unsupported value of [dataset.source]:" + source);
    }

    @Bean("salaryCsv")
    public SalaryRepository salaryCsvRepository(@Value("${dataset.csv}") String csvFile) {
        return new SalaryRepositoryCsv(csvFile);
    }
    @Bean
    @Qualifier("salaryJson")
    public SalaryRepository salaryJsonRepository(@Value("${dataset.json}") String csvFile) {
        return new SalaryRepositoryJson(csvFile);
    }
}
