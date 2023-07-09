# salary-datasets
A simple spring boot rest service uses to query, sort, and display salary data
 
## Prerequisite
1. Java version 17 or higher. Get it [here](https://duckduckgo.com/?q=java+download)
2. Maven version 3.6.3 or higher. Get it [here](https://duckduckgo.com/?q=maven+download)
3. (Optional) Your favorite IDE

## Getting Started

Data source can be specified as either JSON or CSV. You can switch the source by editing `dataset.source` configuration in `application.yml`. The source filename is also configurable.
Default data source is JSON.

When you are ready, do the following:
- At your command line, type `mvn spring-boot:run`
- Or, from IDE, run `Application` class


It might take a while to download project's dependencies.

## Usage
The service can be accessed at `http://localhost:8080/salary/job_data`

### Filter
Data can be filted by specifying field name(s) to filter, enclosed by brackets with desired operation, followed by the filtering value. However, `gender` doesn't need operation.

The service supports only 3 fields for fitlering, which are `salary`, `job_title`, and `gender`. All filtered conditions will be `AND` together. `OR` is not supported.

Operation can be `[gt]` for greater than, `[gte]` for greater than or equal, `[eq]` for equal, `[lte]` for less than or equal, and `[lt]` for less than.

For example, if you want to search for employees with salary less than 1000 and job title equal "Developer" and is Male, use this synxtax
```
http://localhost:8080/salary/job_data?salary[gt]=120000?job_title[eq]=Developer?gender=Male
```
Job title's value is case-sensitive.
Gender can be entered with either Male, M, Female, F.

### Order
Data can be sorted by started with `sort` paramter name, specifying field name(s) to order, separate with `,` (comma), and enclosed by brackets with order direction

Fields that can be ordered are employer, location, job_title, years_at_employer, years_of_experience, salary, signing_bonus, annual_bonus, annual_stock_value_bonus, gender

The field that specify first has more sorting priority than the trailing fields.

For example, if you want to sort data by year_of_experience in ascending order, and salary in descending order, use this syntax
```
http://localhost:8080/salary/job_data?&sort=year_of_experience[asc],salary[desc]
```

### Selectable Column
You can specify which column(s) should be displayed. This can be done by started with query parameter `fields`, than enter the columns you want to be shown. However, the ordering of each columns can't be changed. It uses the preset ordering.

Fields that can be ordered are timestamp, employer, location, job_title, years_at_employer, years_of_experience, salary, signing_bonus, annual_bonus, annual_stock_value_bonus, gender, additional_comments

If this query is not present, the default is to show all columns. If you specify the wrong name, that column will not be shown. It means it will show blank page if the columns you specify are all invalid.

For example, if you want to view only employee and annual_bonus fields, use this syntax
```
http://localhost:8080/salary/job_data?&fields=employee,annual_bonus
```

### Mix them
You can use all 3 parameters in a single request. For example, this request URL is valid
```
http://localhost:8080/salary/job_data?salary[gt]=120000&job_title[eq]=System Engineer&sort=salary[asc],gender[asc]&fields=employer,salary,annual_bonus,years_at_employer
```

## Limitations
Currenly, there is no input validation. It means if the query parameters is not entered properly, the system could either discard the invalid parameters or throw an unrecoverable exception.

Since there is no exception handler, the page will display the spring default exception page.

Database as source is not supported. However, you can create a database datasource and inject them to the service class.

Data parsing is not perfect. Some invalid data are discarded while the other are parsed incorrectly. Since it takes too much time, the first release just use simple parser. You may not find the rows you are filtered even though you correctly state the query value.

## License
I want it free to distribute and use. But as of now, it's disbuted with [MIT](https://choosealicense.com/licenses/mit/) license.
See `License.txt`
