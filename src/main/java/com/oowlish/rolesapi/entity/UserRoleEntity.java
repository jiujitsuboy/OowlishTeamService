package com.oowlish.rolesapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name="ID_TEAM")
  private String idTeam;

  @Column(name="ID_USER", nullable = false)
  private String idUser;

  @OneToOne(cascade = CascadeType.ALL)
  private RoleEntity role;
}
