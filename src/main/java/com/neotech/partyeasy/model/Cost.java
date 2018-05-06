package com.neotech.partyeasy.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cost_table")
@EntityListeners(AuditingEntityListener.class)
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private long cost_id;

  /*  @OneToOne(mappedBy = "cost", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(hidden = true)
    private PartySpace partySpace;*/

    private double weekend_cost;
    private double weekday_cost;

    @Column(name="date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Date date_created;

    @Column(name="last_updated", nullable = false)
    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Date last_updated;

    public Cost() {
    }

    public Cost(double weekend_cost, double weekday_cost) {
        this.weekend_cost = weekend_cost;
        this.weekday_cost = weekday_cost;
    }

   /* public PartySpace getPartySpace() {
        return partySpace;
    }

    public void setPartySpace(PartySpace partySpace) {
        this.partySpace = partySpace;
    }*/

    public long getCost_id() {
        return cost_id;
    }

    public void setCost_id(long cost_id) {
        this.cost_id = cost_id;
    }

    public double getWeekend_cost() {
        return weekend_cost;
    }

    public void setWeekend_cost(double weekend_cost) {
        this.weekend_cost = weekend_cost;
    }

    public double getWeekday_cost() {
        return weekday_cost;
    }

    public void setWeekday_cost(double weekday_cost) {
        this.weekday_cost = weekday_cost;
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
