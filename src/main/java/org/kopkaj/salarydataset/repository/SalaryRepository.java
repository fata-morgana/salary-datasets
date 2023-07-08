package org.kopkaj.salarydataset.repository;

import org.kopkaj.salarydataset.model.SalaryDataset;

import java.util.List;

public interface SalaryRepository {
    public List<SalaryDataset> getAll();
}
