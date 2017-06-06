package com.excilys.computerdatabase.services.interfaces.template;

import org.springframework.security.access.annotation.Secured;

import com.excilys.computerdatabase.dtos.Page;

//@Secured("ROLE_USER")
public interface PageService<T> {

    /**
     * Returns a page with all the objects.
     * @return Page T
     */
    Page<T> getPage();

    /**
     * Returns the page numero pageNumero of length length.
     * @param pageNumero the numero of the page
     * @param length the length of the page
     * @return Page T
     */
    Page<T> getPage(int pageNumero, int length);

    /**
     * Returns the page numero pageNumero of length length.
     * @param pageNumero the numero of the page
     * @param length the length of the page
     * @param search the field to search for
     * @param order the field to order by
     * @param column the field to sort for
     * @return Page T
     */
    Page<T> getPage(int pageNumero, int length, String search, String order, String column);

    /**
     * Returns the page numero pageNumero of length length.
     * @param page the parameters of the page
     * @return Page T
     */
    Page<T> getPage(Page<T> page);
    

    /**
     * Return the number of computers.
     * @return int number of computers
     * @param search the field to search for
     */
    int count(String search);
}