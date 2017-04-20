package com.excilys.computerdatabase.interfaces;

import java.util.List;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exceptions.ComputerNotFoundException;
import com.excilys.computerdatabase.exceptions.IntroducedAfterDiscontinuedException;
import com.excilys.computerdatabase.exceptions.NameEmptyException;

public interface ComputerService {

    /**
     * Returns the list of the computers.
     * @return List<ComputerDTO>
     */
    List<ComputerDTO> get();

    /**
     * Returns the computer of id id.
     * @param id the id of the computer
     * @return ComputerDTO
     * @throws ComputerNotFoundException exception when the computer is not found
     */
    ComputerDTO getById(long id) throws ComputerNotFoundException;

    /**
     * Returns the list of computers of name name.
     * @param name the name of the computer
     * @return List<ComputerDTO>
     * @throws ComputerNotFoundException exception when the computer is not found
     */
    List<ComputerDTO> getByName(String name) throws ComputerNotFoundException;

    /**
     * Add a computer.
     * @param computerDTO the computer to add
     * @throws IntroducedAfterDiscontinuedException exception when the introduced date is before the dicontinued date
     * @throws NameEmptyException exception when the name is empty
     */
    void add(ComputerDTO computerDTO) throws IntroducedAfterDiscontinuedException, NameEmptyException;

    /**
     * Update a computer.
     * @param computerDTO the computer to update
     * @throws ComputerNotFoundException exception when the computer is not found
     * @throws IntroducedAfterDiscontinuedException exception when the introduced date is before the dicontinued date
     * @throws NameEmptyException exception when the name is empty
     */
    void update(ComputerDTO computerDTO) throws IntroducedAfterDiscontinuedException, ComputerNotFoundException, NameEmptyException;

    /**
     * Delete a computer.
     * @param id the id of the computer
     * @throws ComputerNotFoundException exception when the computer is not found
     */
    void delete(long id) throws ComputerNotFoundException;

    /**
     * Return the number of computers.
     * @return int number of computers
     */
    int count();

    /**
     * Returns the company of the computer id.
     * @param id the id of the computer
     * @return CompanyDTO
     */
    CompanyDTO getCompany(long id);
}