package hellojpa;

import javax.persistence.Embeddable;

// 임베디드 타입 구현 (임베디드 타입 구현 클래스에 메소드 사용도 가능)
// 기본 생성자 필수
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(){}
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
