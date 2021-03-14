package de.fritzsche.ooka.controller.enities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Equipment implements Serializable {
    private @Id Long id;
    private Long serviceId;
    private String name;
}
