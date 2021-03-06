package com.excilys.computerdatabase.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.computerdatabase.dtos.ComputerDTO;

public class ComputerDTOValidator implements ConstraintValidator<VerificationComputerDTO, ComputerDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOValidator.class);
    private static final String DATE_FRANCAISE = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-[0-9]{4}";
    private static final String DATE_FRANCAISE_ENVERS = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    private static final String DATE_ANGLAISE = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-[0-9]{4}";


    @Override
    public void initialize(VerificationComputerDTO arg0) {
        
    }

    @Override
    public boolean isValid(ComputerDTO computerDTO, ConstraintValidatorContext arg1) {
        LOGGER.info("-------->isValide(ComputerDTO, validatorContext) args: " + computerDTO);
        boolean nameCorrect = verificationName(computerDTO.getName());
        boolean DateCorrect = (verificationDateValide(computerDTO));
        if (!nameCorrect) {
            String langue  =  !LocaleContextHolder.getLocale().toString().equals("en")? "nom incorrect" : "invalid name" ; 
            arg1.buildConstraintViolationWithTemplate(langue).addPropertyNode("name").addConstraintViolation();
        }
        if (!DateCorrect) {
            String langue  =  !LocaleContextHolder.getLocale().toString().equals("en")? "date incorrecte" : "invalid date" ; 
            arg1.buildConstraintViolationWithTemplate(
                    langue).addPropertyNode("introduced").addConstraintViolation();
            arg1.buildConstraintViolationWithTemplate(
                    langue).addPropertyNode("discontinued").addConstraintViolation();
        }
        return nameCorrect && DateCorrect;

    }

    /**
     * Vérifie si la date est correcte.
     * @param date : tableau de string représentant une date decouper en date et
     *            heure.
     * @param format : format de la date
     * @return boolean : reponse si oui ou non la date est correct
     */
    public static LocalDate stringEnDate(String date, String format) {
        LOGGER.info("-------->StringEnDate(date,format) args: " + date + " - " + format);
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        try {
            return df.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Transforme un string en LocalDate selon la locale.
     * @param date : date en string
     * @return LocalDate : localDate associer au string selon la locale
     */
    public static LocalDate getLocalDate(String date) {
        LOGGER.info("-------->getLocalDate(date) args: " + date);
        Locale local = LocaleContextHolder.getLocale();
        if (local.toString().equals("fr_fr")||(local.toString().equals("fr"))) {
            if (Pattern.matches(DATE_FRANCAISE, date)) {
                return stringEnDate(date, "dd-MM-yyyy");
            }
            if (Pattern.matches(DATE_FRANCAISE_ENVERS, date)) {
                return stringEnDate(date, "yyyy-MM-dd");
            }
        } else {
            if (Pattern.matches(DATE_ANGLAISE, date)) {
                return stringEnDate(date, "MM-dd-yyyy");
            }
        }
        return null;
    }

    /**
     * Verifie que les dates de computerDto sont valide.
     * @param introducedDTO : date introduced en String
     * @param discontinuedDTO : date discontinued en String
     * @return boolean : si la date est valide
     */
    public boolean verificationDateValide(ComputerDTO computer) {
        LOGGER.info("-------->verificationDateValide(computer) args: " +computer);
        if (formatDate(computer.getIntroduced()) && (formatDate(computer.getDiscontinued()))) {
            LocalDate introduced = getLocalDate(computer.getIntroduced());
            LocalDate discontinued = getLocalDate(computer.getDiscontinued());
            return dateLogique(introduced, discontinued, computer);
        }
        return false;
    }

    /**
     * verifie que le format de la date est correct.
     * @param date : date en string
     * @return boolean : si oui ou non le format est correct
     */
    public static boolean formatDate(String date) {
        LOGGER.info("-------->getLocalDate(date) args: " + date);
        Locale local = LocaleContextHolder.getLocale();
        if ((local.toString().equals("fr_fr"))||(local.toString().equals("fr"))) {
            return (Pattern.matches(DATE_FRANCAISE, date)) || (Pattern.matches(DATE_FRANCAISE_ENVERS, date))
                    || (date.equals(""));
        } else {
            return ((Pattern.matches(DATE_ANGLAISE, date)) || (date.equals("")));
        }
    }

    /**
     * Vérifie la logique entre les dates.
     * @param introduced : date de debut
     * @param discontinued : date de fin
     * @return boolean : si les 2 valeurs sont logique entre elles
     */
    public static boolean dateLogique(LocalDate introduced, LocalDate discontinued, ComputerDTO computer) {
        LOGGER.info("-------->dateLogique(introduced,discontinued) args: " + introduced + " - " + discontinued);
        if ((introduced == null) && (discontinued == null)) {
            return true;
        }
        if ((introduced == null) && (discontinued != null)) {
            return false;
        }
        if ((introduced != null) && (discontinued == null)) {
            computer.setIntroduced(introduced.toString());
            return true;
        }
        if ((introduced == discontinued) || (introduced.equals(discontinued))) {
            computer.setIntroduced(introduced.toString());
            computer.setDiscontinued(discontinued.toString());
            return true;
        }
        computer.setIntroduced(introduced.toString());
        computer.setDiscontinued(discontinued.toString());
        return introduced.isBefore(discontinued);
    }

    /**
     * indique le nom du computerDTO est correct (non null, non vide).
     * @param name : string name
     * @return boolean : si oui ou non le nom est correct
     */
    public static boolean verificationName(String name) {
        LOGGER.info("-------->verificationName(name) args: " + name);
        if (name == null) {
            return false;
        }
        return !name.equals("") && name.equals(name.replaceAll("[^(\\d\\s\\w\\.\\-)]|_", " "));
    }
}
