package com.flaminiovilla.geopic.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flaminiovilla.geopic.model.Region;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
@Builder
public class User {

   @JsonIgnore
   @Id
   @Column(name = "ID")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
   @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
   private Long id;

   @Column(name = "email", length = 50, unique = true)
   @NotNull
   @Size(min = 4, max = 50)
   private String email;

   @JsonIgnore
   @Column(name = "password", length = 100)
   @NotNull
   @Size(min = 4, max = 100)
   private String password;

   @Column(name = "firstName", length = 50)
   @NotNull
   @Size(min = 1, max = 50)
   private String firstName;

   @Column(name = "lastName", length = 50)
   @NotNull
   @Size(min = 1, max = 50)
   private String lastName;

   @JsonIgnore
   @Column(name = "activated")
   @NotNull
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "USER_AUTHORITY",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
   @BatchSize(size = 20)
   private Set<Authority> authorities = new HashSet<>();


   @ManyToOne
   @JoinColumn(name="region_ID")
   private Region region;


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id.equals(user.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

}
