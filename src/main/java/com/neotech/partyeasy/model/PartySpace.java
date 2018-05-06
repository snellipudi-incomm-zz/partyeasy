package com.neotech.partyeasy.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "party_space_table")
@EntityListeners(AuditingEntityListener.class)
public class PartySpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private long party_space_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String incharge;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="address_id")
    private Address address;

    @Column(nullable = false)
    private int capacity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cost_id")
    private Cost cost;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String description;

    @ElementCollection
    private List<String> holidaysList;

    @Column(name="date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Date date_created;

    @Column(name="last_updated", nullable = false)
    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Date last_updated;



    public PartySpace() {
    }

    public PartySpace(String name, String incharge, Address address, int capacity, Cost cost, String email, String password, String description, List<String> holidaysList) {
        this.name = name;
        this.incharge = incharge;
        this.address = address;
        this.capacity = capacity;
        this.cost = cost;
        this.email = email;
        this.password = password;
        this.description = description;
        this.holidaysList = holidaysList;
    }



    public long getParty_space_id() {
        return party_space_id;
    }

    public void setParty_space_id(long party_space_id) {
        this.party_space_id = party_space_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHolidaysList() {
        return holidaysList;
    }

    public void setHolidaysList(List<String> holidaysList) {
        this.holidaysList = holidaysList;
    }

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
}
