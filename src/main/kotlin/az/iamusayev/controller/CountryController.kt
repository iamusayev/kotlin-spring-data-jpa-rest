package az.iamusayev.controller

import az.iamusayev.dto.CountryDto
import az.iamusayev.service.CountryService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/countries")
class CountryController(
    private val countryService: CountryService
) {

    @GetMapping
    fun getAll(@RequestParam("page") pageIndex: Int): List<CountryDto> = countryService.getAll(pageIndex)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CountryDto = countryService.getById(id)

    @GetMapping("/search")
    fun searchCountries(@RequestParam("prefix") prefix: String): List<CountryDto> = countryService.search(prefix)

    @PostMapping
    fun create(@RequestBody countryDto: CountryDto): Int = countryService.create(countryDto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody countryDto: CountryDto) =
        countryService.update(id, countryDto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = countryService.delete(id)

    @GetMapping("/population")
    fun getByPopulationMoreThan(@RequestParam("population") population: Int) = countryService.getByPopulationMoreThan(population)
}