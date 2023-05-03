package az.iamusayev.service

import az.iamusayev.dto.CountryDto
import org.springframework.stereotype.Service

@Service
interface CountryService {
    fun getAll(pageIndex: Int): List<CountryDto>

    fun getById(id: Int): CountryDto

    fun search(prefix: String): List<CountryDto>

    fun create(countryDto: CountryDto): Int

    fun update(id: Int, countryDto: CountryDto)

    fun delete(id: Int)

    fun getByPopulationMoreThan(population: Int): List<CountryDto>
}