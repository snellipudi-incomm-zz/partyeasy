package com.neotech.partyeasy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "address_table")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private long address_id;

  /*  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(hidden = true)
    private PartySpace partySpace;*/

    @Column(nullable = false)
    private String pincode;

    private String door_number;

    @Column(nullable = false)
    private String address_line1;

    private String address_line2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phone_number1;

    private String phone_number2;

    @Column(name="date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Date date_created;

    @Column(name="last_updated", nullable = false)
    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Date last_updated;



    public Address() {
    }

    public Address(String pincode, String door_number, String address_line1, String address_line2, String city, String phone_number1, String phone_number2) {
        this.pincode = pincode;
        this.door_number = door_number;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.city = city;
        this.phone_number1 = phone_number1;
        this.phone_number2 = phone_number2;
    }

   /* public PartySpace getPartySpace() {
        return partySpace;
    }

    public void setPartySpace(PartySpace partySpace) {
        this.partySpace = partySpace;
    }*/

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number1() {
        return phone_number1;
    }

    public void setPhone_number1(String phone_number1) {
        this.phone_number1 = phone_number1;
    }

    public String getPhone_number2() {
        return phone_number2;
    }

    public void setPhone_number2(String phone_number2) {
        this.phone_number2 = phone_number2;
    }
}
