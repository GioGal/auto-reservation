package it.appforsports.auto.reservation.service.mapper;

import it.appforsports.auto.reservation.domain.*;
import it.appforsports.auto.reservation.service.dto.AutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auto and its DTO AutoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutoMapper extends EntityMapper<AutoDTO, Auto> {

    

    

    default Auto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auto auto = new Auto();
        auto.setId(id);
        return auto;
    }
}
