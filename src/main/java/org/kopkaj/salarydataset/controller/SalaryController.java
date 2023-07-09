package org.kopkaj.salarydataset.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kopkaj.salarydataset.model.SalaryDataset;
import org.kopkaj.salarydataset.service.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
    @GetMapping(path="job_data", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String get(@RequestParam LinkedHashMap<String,String> allRequestParams) {
        logger.info("request: " + allRequestParams);
        List<SalaryDataset> result = salaryService.queryWithParameters(allRequestParams);
        logger.info("start of response...");
        result.stream().forEach(i -> logger.info(i.toString()));
        logger.info("end of response...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            logger.error("Cannot serialize result to json object.", e);
            throw new RuntimeException(e);
        }
    }
}
