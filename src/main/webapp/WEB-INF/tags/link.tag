<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ attribute name="href" required="true" rtexprvalue="true"%>

<%@ attribute name="page" required="false" type="java.lang.Integer"%>
<%@ attribute name="length" required="false" type="java.lang.Integer"%>

<%@ attribute name="search" required="false" type="java.lang.String"%>
<%@ attribute name="sort" required="false" type="java.lang.String"%>
<%@ attribute name="order" required="false" type="java.lang.String"%>
<%@ attribute name="text" required="false" type="java.lang.String"%>

<%@ attribute name="previous" required="false" type="java.lang.Boolean"%>
<%@ attribute name="next" required="false" type="java.lang.Boolean"%>
<%@ attribute name="home" required="false" type="java.lang.Boolean"%>

<%@ attribute name="classe" required="false" type="java.lang.String"%>

<%@ attribute name="button" required="false" type="java.lang.String"%>
<%@ attribute name="li" required="false" type="java.lang.String"%>

<%
    previous 				= (previous	!= null ? previous 	: false);
    next 					= (next 	!= null ? next 		: false);
    
    page					= (page 	!= null ? page 		: 1);
    length					= (length 	!= null ? length 	: 10);

    String output 			= "";
    String pageAdresse 		= "?page=" + String.valueOf(page);
    String lengthAdresse 	= "&length=" + String.valueOf(length);

    String searchAdresse 	= (search	!= null && !search.equals("") 	? "&search=" 	+ search 	: "");
    String sortAdresse 		= (sort 	!= null && !sort.equals("") 	? "&sort=" 		+ sort 		: "");
    String orderAdresse 	= (order 	!= null && !order.equals("") 	? "&order=" 	+ order 	: "");
    
    text 					= (text != null ? text : (home != null && home ? " Application - Computer Database " : ""));

    String adresse 			= href + pageAdresse + lengthAdresse + searchAdresse + sortAdresse + orderAdresse;

    //Si on veut que le lien soit un bouton de type button
    if (button != null) {
        text = "<button type=\"button\" class=\"btn btn-" + button + "\">" + text + "</button>";
    }

    //Si on veut que le lien soit un lien précédent ou suivant
    if (previous || next) {

        //la page en string (page + ou - 1)
        pageAdresse = "?page=" + String.valueOf(page + (previous ? -1 : 1));

        //l'adresse avec les champs search, sort et order facultatifs
        adresse = href + pageAdresse + lengthAdresse + searchAdresse + sortAdresse + orderAdresse;

        //la balise a avec les parametres correspondants (previous ou next)
        output = "<a href=\"" + adresse + "\" aria-label=\"" + (previous ? "Next" : "Previous")
                + "\"> <span aria-hidden=\"true\">&" + (previous ? 'l' : 'r') + "aquo;</span></a>";

    } else {

        //la balise a avec les parametres correspondants
        output = "<a class=\"" + classe + "\" href=\"" + adresse + "\">" + text + "</a>";
    }

    if (li != null) {
        output = "<li" + (!li.equals("default") ? " class=" + li + " " : "") + ">" + output + "</li>";
    }

    out.println(output);
%>