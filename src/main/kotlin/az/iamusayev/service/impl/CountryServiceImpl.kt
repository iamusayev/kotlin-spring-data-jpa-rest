package az.iamusayev.service.impl

import az.iamusayev.dto.CityDto
import az.iamusayev.dto.CountryDto
import az.iamusayev.entity.CityEntity
import az.iamusayev.entity.CountryEntity
import az.iamusayev.exception.CountryNotFoundException
import az.iamusayev.repository.CityRepository
import az.iamusayev.repository.CountryRepository
import az.iamusayev.service.CountryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository
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

    @Transactional
    override fun create(dto: CountryDto): Int {
        val countryEntity = countryRepository.save(dto.toEntity())
        val cities = dto.cities.map { it.toEntity(countryEntity) }
        cityRepository.saveAll(cities)
        return countryEntity.id;
    }

    @Transactional
    override fun update(id: Int, dto: CountryDto) {
        var existingCountry = fetchCountryIfExist(id)

        existingCountry.name = dto.name
        existingCountry.population = dto.population

        existingCountry = countryRepository.save(existingCountry)

        val cities = dto.cities.map { it.toEntity(existingCountry) }
        cityRepository.deleteAllByCountry(existingCountry)
        cityRepository.saveAll(cities)
    }

    @Transactional
    override fun delete(id: Int) {
        val existingCountry = fetchCountryIfExist(id)
        cityRepository.deleteAllByCountry(existingCountry)
        countryRepository.delete(existingCountry)
    }

    override fun search(prefix: String): List<CountryDto> = countryRepository.findByNameStartsWithIgnoreCaseOrderByName(prefix).map { it.toDto() }

    override fun getByPopulationMoreThan(population: Int) = countryRepository.findAllCountryEntityByPopulationAfter(population).map { it.toDto() }

    private fun fetchCountryIfExist(id: Int) = countryRepository.findByIdOrNull(id) ?: throw CountryNotFoundException(id)

    private fun CountryEntity.toDto(): CountryDto {
        return CountryDto(id = this.id, name = this.name, population = this.population, cities = this.cities.map { it.toDto() })
    }

    private fun CityEntity.toDto(): CityDto {
        return CityDto(id = this.id, name = this.name)
    }

    private fun CountryDto.toEntity(): CountryEntity = CountryEntity(id = 0, name = this.name, population = this.population)

    private fun CityDto.toEntity(country: CountryEntity): CityEntity =
        CityEntity(id = 0, name = this.name, country = country)
}