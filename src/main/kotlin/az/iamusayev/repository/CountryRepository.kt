package az.iamusayev.repository

import az.iamusayev.entity.CountryEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository


interface CountryRepository : CrudRepository<CountryEntity, Int> {

    fun findByOrderByNameDesc(pageable: Pageable): List<CountryEntity>

    fun findByNameStartsWithIgnoreCaseOrderByName(prefix: String): List<CountryEntity>

    fun findAllCountryEntityByPopulationAfter(population: Int): List<CountryEntity>
}