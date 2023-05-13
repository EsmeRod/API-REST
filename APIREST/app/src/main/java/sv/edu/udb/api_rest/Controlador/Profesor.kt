package sv.edu.udb.api_rest.Controlador

data class Profesor (
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val materia: String
)