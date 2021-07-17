package com.imagerepository;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "test")
public class model {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // i think this needs to be identity
    @Column(name = "id")
    private Integer id;


    @Column(name = "name")
    private String name;


    @Column(name = "type")
    private String type;

    @Column(name = "picByte")
    private byte[] picByte;

    public model(){
        super();
    }

    public model(String name, String type, byte[] picByte){
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    //generate getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}
