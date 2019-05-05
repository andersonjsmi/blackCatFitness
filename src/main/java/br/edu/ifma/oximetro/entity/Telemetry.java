/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author anderson
 */
@Entity
public class Telemetry implements Serializable {

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Session session;
    private String deviceName;
    private Float bpm;
    private Integer spo2;
    private Integer zone;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telemetry)) {
            return false;
        }
        Telemetry other = (Telemetry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.onlinemarket.oximetro.Oximetro[ id=" + id + " ]";
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return the bpm
     */
    public Float getBpm() {
        return bpm;
    }

    /**
     * @param bpm the bpm to set
     */
    public void setBpm(Float bpm) {
        this.bpm = bpm;
    }

    /**
     * @return the spo2
     */
    public Integer getSpo2() {
        return spo2;
    }

    /**
     * @param spo2 the spo2 to set
     */
    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    /**
     * @return the created_at
     */
    public Calendar getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(Calendar created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the zone
     */
    public Integer getZone() {
        return zone;
    }

    /**
     * @param zone the zone to set
     */
    public void setZone(Integer zone) {
        this.zone = zone;
    }
    
}
