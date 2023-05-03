package az.iamusayev.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.*

@Entity
@Table(name = "country")
class CountryEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int = 0,
    @Column(name = "name")
    var name: String = "",
    @Column(name = "population")
    var population: Int = 0
)