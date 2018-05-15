package com.malenko.rest.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<ID> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private ID id;

  @CreatedDate
  @Temporal(TIMESTAMP)
  private Date createdAt;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  private Date updatedAt;

}
