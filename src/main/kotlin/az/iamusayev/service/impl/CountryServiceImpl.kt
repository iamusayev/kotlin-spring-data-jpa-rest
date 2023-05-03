package az.iamusayev.service.impl

import az.iamusayev.dto.CountryDto
import az.iamusayev.entity.CountryEntity
import az.iamusayev.exception.CountryNotFoundException
import az.iamusayev.repository.CountryRepository
import az.iamusayev.service.CountryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository
) : CountryService {
    override fun getAll(pageIndex: Int): List<CountryDto> {
        return countryRepository.findByOrderByNameDesc(PageRequest.of(pageIndex, 2)).map {
            it.toDto()
        }
    }

    override fun getById(id: Int): CountryDto {
        return countryRepository.findByIdOrNull(id)
            ?.toDto()
            ?: throw CountryNotFoundException(id)
    }

    override fun create(dto: CountryDto): Int {
        return countryRepository.save(dto.toEntity()).id
    }

    override fun update(id: Int, dto: CountryDto) {
        val existingCountry = fetchCountryIfExist(id)

        existingCountry.name = dto.name
        existingCountry.population = dto.population

        countryRepository.save(dto.toEntity())
    }

    override fun delete(id: Int) {
        val existingCountry = fetchCountryIfExist(id)
        countryRepository.delete(existingCountry)
    }

    override fun search(prefix: String): List<CountryDto> = countryRepository.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }

    override fun getByPopulationMoreThan(population: Int) = countryRepository.findAllCountryEntityByPopulationAfter(population).map { it.toDto() }


    private fun fetchCountryIfExist(id: Int) = countryRepository.findByIdOrNull(id) ?: throw CountryNotFoundException(id)

    private fun CountryEntity.toDto(): CountryDto {
        return CountryDto(id = this.id, name = this.name, population = this.population)
    }

    private fun CountryDto.toEntity(): CountryEntity = CountryEntity(id = 0, name = this.name, population = this.population)
}