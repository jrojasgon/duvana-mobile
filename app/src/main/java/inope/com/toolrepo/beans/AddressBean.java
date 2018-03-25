package inope.com.toolrepo.beans;

import java.io.Serializable;

public class AddressBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String street;
    private String complementStreet;
    private String zipCode;
    private String city;
    private String country;
    private String neighborhood;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplementStreet() {
        return complementStreet;
    }

    public void setComplementStreet(String complementStreet) {
        this.complementStreet = complementStreet;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
