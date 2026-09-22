package com.haynespro.assessment.mmt.api.infrastructure.adapters.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "MMT_TYPE")
public class TypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "MODEL_ID", nullable = false)
  private ModelEntity modelEntity;

  private String name;

  @Column(name = "buildYear")
  private String year;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ModelEntity getModelEntity() {
    return modelEntity;
  }

  public void setModelEntity(ModelEntity modelEntity) {
    this.modelEntity = modelEntity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TypeEntity)) {
      return false;
    }
    TypeEntity that = (TypeEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "TypeEntity{" + "id=" + id + ", name='" + name + '\'' + ", year='" + year + '\'' + '}';
  }
}
