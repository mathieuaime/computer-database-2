package com.excilys.computerdatabase.services.interfaces;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.excilys.computerdatabase.exceptions.CompanyNotFoundException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.models.Page;
import com.excilys.computerdatabase.services.interfaces.template.CrudService;
import com.excilys.computerdatabase.services.interfaces.template.PageService;

//@Secured("ROLE_USER")
public interface CompanyService extends CrudService<Company, Company>, PageService<Company> {
    /**
     * Returns the list of the computers of the company id.
     * @param id the id of the company
     * @return List ComputerDTO
     * @throws CompanyNotFoundException Company Not Found
     */
    List<Computer> getComputers(long id) throws CompanyNotFoundException;
}
