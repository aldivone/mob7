package br.com.localiza.mob7.pontodeinteresse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoDeInteresseRepository extends JpaRepository<PontoDeInteresse, Long> {
	
}
