package ml.pic.tech.app.alimentation.utils;

import org.thymeleaf.util.ArrayUtils;

public interface Endpoint {
    //Endpoint sur les controllers
    String APPROVISION_ENDPOINT = "/approvision";
    String CATEGORIE_ENDPOINT = "/categorie";
    String COMMANDE_ENDPOINT = "/commande";
    String DEPENSE_ENDPOINT = "/depense";
    String DETTE_CLIENT_ENDPOINT = "/dette-client";
    String MAGASIN_ENDPOINT = "/magasin";
    String CLIENT_FOURNISSEUR_ENDPOINT = "/client-fournisseur";
    String PRODUIT_ENDPOINT = "/produit";
    String RETOUR_PRODUIT_ENDPOINT = "/retour-produit";
    String STOCK_ENDPOINT = "/stock";
    String TRANSFERT_STOCK_ENDPOINT = "/transfert-stock";
    String UTILISATEUR_ENDPOINT = "/utilisateur";
    String VENTE_ENDPOINT = "/vente";




    //Endpoint sur les methodes dans les controllers
    String AJOUT_ENDPOINT = "/add";
    String UPDATE_ENDPOINT = "/update";
    String DELETE_ENDPOINT = "/delete";
    String INFO_ENDPOINT = "/info";
    String LISTE_ENDPOINT = "/liste";
    String AUDIT_ENDPOINT = "/audit";
    String DETAILS_ENDPOINT ="/details" ;

    //Endpoint sur les fonctionnalites utiles
    String PASSWORD_ENDPOINT = "/password";
    String ERROR_ENDPOINT = "/error";
    String ACCEUIL_ENDPOINT = "/";
    String LOGIN_ENDPOINT = "/login";

    String ALL_RESSOURCE="/**/**";

    String CSS="/css/**";
    String JS="/js/**";
    String IMAGE="/image/**";
    String WEBFONTS="/webfonts/**";


    //Ressources par role
    String[] WHITE_LIST={LOGIN_ENDPOINT, ERROR_ENDPOINT, CSS, JS, WEBFONTS, IMAGE};
    String[] UTILISATEUR_ROLE_ACCESS={ALL_RESSOURCE,UTILISATEUR_ENDPOINT+PASSWORD_ENDPOINT };
    String[] ADMINSTRATEUR_ROLE_ACCESS={ UTILISATEUR_ENDPOINT+ALL_RESSOURCE};


    String CHART_VENTE_BETWENN_ENDPOINT = "chart-vente-between";
    String CHART_VENTE_ENDPOINT = "chart-vente";
}
