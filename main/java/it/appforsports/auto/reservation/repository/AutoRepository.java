package it.appforsports.auto.reservation.repository;

import it.appforsports.auto.reservation.domain.Auto;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Auto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
	
	@Query("select a from Auto a where a.statoPrenotazione = 'disponibile'")
	List<Auto> findAvailableAutos();

}
