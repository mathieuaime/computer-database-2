package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.excilys.computerdatabase.daos.CompanyDAOImpl;
import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class CompanyMapper {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Create a company from a ResultSet.
     * @param rset the ResultSet
     * @return Company
     */
    public static Company getCompany(ResultSet rset) {

        Company company = null;

        try {
            int idCompany       = rset.getInt(Company.FIELD_ID);
            String nameCompany  = rset.getString(Company.FIELD_NAME);
            company = new Company.Builder(nameCompany).id(idCompany).build();
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exception: " + e);
            }
        }

        return company;
    }
    
    public static CompanyDTO createDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        
        CompanyDAOImpl companyDAO = new CompanyDAOImpl(); 

        for (Computer c : companyDAO.getComputers(company.getId())) {
            companyDTO.getComputersList().add(c.getId());
        }

        return companyDTO;
    }

    public static Company createBean(CompanyDTO companyDTO) {
        return new Company.Builder(companyDTO.getName()).id(companyDTO.getId()).build();
    }
}