package az.iamusayev.exception

import org.springframework.http.HttpStatus

class CountryNotFoundException(countryId: Int) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        "country.not.found",
        "Country not found with id=$countryId"
    )
)