package com.haynespro.assessment.mmt.api.infrastructure.adapters.entities;

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
@Table(name = "MMT_MODEL")
public class ModelEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "MAKE_ID", nullable = false)
  private MakeEntity makeEntity;

  private String category;

  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public MakeEntity getMakeEntity() {
    return makeEntity;
  }

  public void setMakeEntity(MakeEntity makeEntity) {
    this.makeEntity = makeEntity;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelEntity)) {
      return false;
    }
    ModelEntity that = (ModelEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "ModelEntity{"
        + "id="
        + id
        + ", category='"
        + category
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
