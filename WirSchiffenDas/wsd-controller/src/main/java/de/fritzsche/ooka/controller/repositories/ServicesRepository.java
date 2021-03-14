package de.fritzsche.ooka.controller.repositories;

import de.fritzsche.ooka.controller.enities.OptionalEquipment;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentRepository extends CrudRepository<OptionalEquipment, Integer> {

}
