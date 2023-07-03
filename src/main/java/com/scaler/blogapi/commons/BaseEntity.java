package com.scaler.blogapi.commons;

import lombok.Getter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Id //used to mark the primary key of the table
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //used to generate the value of the primary key
    Integer id;

    @CreatedDate() //springdataJPA automatically populates this field with the timestamp when it was first saved.
    @Column(name = "created_at", updatable = false) // this annotation is used to mark the column name in the table
    Date createdAt;

}
