package az.iamusayev.exception

data class ApiError(
    val errorCode: String,
    val description: String
)