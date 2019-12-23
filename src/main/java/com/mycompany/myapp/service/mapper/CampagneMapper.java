package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CampagneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campagne} and its DTO {@link CampagneDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CampagneMapper extends EntityMapper<CampagneDTO, Campagne> {

    @Mapping(source = "campagneName.id", target = "campagneNameId")
    CampagneDTO toDto(Campagne campagne);

    @Mapping(target = "banners", ignore = true)
    @Mapping(target = "removeBanner", ignore = true)
    @Mapping(source = "campagneNameId", target = "campagneName")
    Campagne toEntity(CampagneDTO campagneDTO);

    default Campagne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campagne campagne = new Campagne();
        campagne.setId(id);
        return campagne;
    }
}
