package az.iamusayev.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "city")
class CityEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int = 0,
    var name: String = "",
    @ManyToOne
    @JoinColumn(name = "country_id")
    var country: CountryEntity = CountryEntity()
)