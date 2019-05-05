/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author anderson
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    
    public Float getMHR(){
        return (float) (208 - (0.7 * this.getAge()));
    }
    
    public Integer getBPMZone(Float hr){
        if((hr >= (float)(getMHR() * 0.5)) && (hr <= (float)(getMHR() * 0.6)))
            return 1;
        else if((hr >= (float)(getMHR() * 0.6)) && (hr <= (float)(getMHR() * 0.7)))
            return 2;
        else if((hr >= (float)(getMHR() * 0.7)) && (hr <= (float)(getMHR() * 0.8)))
            return 3;
        else if((hr >= (float)(getMHR() * 0.8)) && (hr <= (float)(getMHR() * 0.9)))
            return 4;
        else if((hr >= (float)(getMHR() * 0.9)) && (hr <= getMHR()))
            return 5;
        
        return 0;
    }
    
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ifma.oximetro.Person[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }
    

}
