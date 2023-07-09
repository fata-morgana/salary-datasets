package org.kopkaj.salarydataset.controller;

import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.service.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
    @GetMapping("job_data")
    public @ResponseBody List<SalaryDataset> get(@RequestParam LinkedHashMap<String,String> allRequestParams) {
        logger.info("request: " + allRequestParams);
        List<SalaryDataset> result = salaryService.queryWithParameters(allRequestParams);
        logger.info("start of response...");
        result.stream().forEach(i -> logger.info(i.toString()));
        logger.info("end of response...");
        return result;
    }
}
