package it.appforsports.auto.reservation.service;

import it.appforsports.auto.reservation.domain.Auto;
import it.appforsports.auto.reservation.repository.AutoRepository;
import it.appforsports.auto.reservation.service.dto.AutoDTO;
import it.appforsports.auto.reservation.service.mapper.AutoMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Auto.
 */
@Service
@Transactional
public class AutoService {

    private final Logger log = LoggerFactory.getLogger(AutoService.class);

    private final AutoRepository autoRepository;

    private final AutoMapper autoMapper;

    public AutoService(AutoRepository autoRepository, AutoMapper autoMapper) {
        this.autoRepository = autoRepository;
        this.autoMapper = autoMapper;
    }

    /**
     * Save a auto.
     *
     * @param autoDTO the entity to save
     * @return the persisted entity
     */
    public AutoDTO save(AutoDTO autoDTO) {
        log.debug("Request to save Auto : {}", autoDTO);
        Auto auto = autoMapper.toEntity(autoDTO);
        auto = autoRepository.save(auto);
        return autoMapper.toDto(auto);
    }

    public AutoDTO reserve(Long id) {
    	AutoDTO autoFounded = findOne(id);
    	
    	if(autoFounded.getStatoPrenotazione().equals("disponibile")) {
    		autoFounded.setStatoPrenotazione("prenotata");
        	return save(autoFounded);
    	}
    	
    	return null;
    }

    /**
     *  Get all the autos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Autos");
        return autoRepository.findAll(pageable)
            .map(autoMapper::toDto);
    }
    
    /**
     *  Get all the available autos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AutoDTO> findAllAvailableAutos() {
        log.debug("Request to get all available Autos");
        return autoMapper.toDto(autoRepository.findAvailableAutos());
    }

    /**
     *  Get one auto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AutoDTO findOne(Long id) {
        log.debug("Request to get Auto : {}", id);
        Auto auto = autoRepository.findOne(id);
        return autoMapper.toDto(auto);
    }

    /**
     *  Delete the  auto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auto : {}", id);
        autoRepository.delete(id);
    }
}
