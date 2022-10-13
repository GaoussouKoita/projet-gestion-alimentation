package ml.pic.tech.app.alimentation.utils;

public interface Endpoint {
    //Endpoint sur les controllers
    String APPROVISSION_ENDPOINT = "/approvission";
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
    String SEARCH_ENDPOINT = "/search";
    String LISTE_ENDPOINT = "/liste";

    //Endpoint sur les fonctionnalites utiles
    String PASSWORD_ENDPOINT = "/password";
    String ERROR_ENDPOINT = "/error";
    String ACCEUIL_ENDPOINT = "/";
    String LOGIN_ENDPOINT = "/login";

    String AUDIT_ENDPOINT = "/audit";
}
