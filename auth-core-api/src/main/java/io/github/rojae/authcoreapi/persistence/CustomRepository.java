package io.github.rojae.authcoreapi.persistence;

import io.github.rojae.authcoreapi.domain.Custom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Custom, Long> {

}
