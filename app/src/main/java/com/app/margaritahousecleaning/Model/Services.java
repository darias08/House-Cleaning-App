package com.app.margaritahousecleaning.Model;

public class Services {
    String residentialCleaning, officeCleaning, apartmentCleaning, constructionCleaning;

    public Services() {
    }

    public Services(String residentialCleaning, String officeCleaning, String apartmentCleaning, String constructionCleaning) {
        this.residentialCleaning = residentialCleaning;
        this.officeCleaning = officeCleaning;
        this.apartmentCleaning = apartmentCleaning;
        this.constructionCleaning = constructionCleaning;
    }

    public String getResidentialCleaning() {
        return residentialCleaning;
    }

    public void setResidentialCleaning(String residentialCleaning) {
        this.residentialCleaning = residentialCleaning;
    }

    public String getOfficeCleaning() {
        return officeCleaning;
    }

    public void setOfficeCleaning(String officeCleaning) {
        this.officeCleaning = officeCleaning;
    }

    public String getApartmentCleaning() {
        return apartmentCleaning;
    }

    public void setApartmentCleaning(String apartmentCleaning) {
        this.apartmentCleaning = apartmentCleaning;
    }

    public String getConstructionCleaning() {
        return constructionCleaning;
    }

    public void setConstructionCleaning(String constructionCleaning) {
        this.constructionCleaning = constructionCleaning;
    }
}
