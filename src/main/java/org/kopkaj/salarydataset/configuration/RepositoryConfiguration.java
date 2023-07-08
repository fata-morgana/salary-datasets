package org.kopkaj.salarydataset.configuration;

import org.kopkaj.salarydataset.repository.SalaryRepository;
import org.kopkaj.salarydataset.repository.SalaryRepositoryCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public SalaryRepository csvSalaryRepository(@Value("{dataset.csv}") String csvFile) {
        return new SalaryRepositoryCsv(csvFile);
    }
}
