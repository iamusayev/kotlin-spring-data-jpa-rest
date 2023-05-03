package az.iamusayev.repository

import az.iamusayev.entity.CityEntity
import az.iamusayev.entity.CountryEntity
import org.springframework.data.repository.CrudRepository

interface CityRepository : CrudRepository<CityEntity, Int> {
    fun deleteAllByCountry(country: CountryEntity)
}