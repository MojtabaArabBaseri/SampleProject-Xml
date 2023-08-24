package ir.millennium.sampleProject.domain.entity

enum class TypeTheme(val typeTheme: Int) {
    LIGHT(1), DARK(2);

    companion object {
        fun create(value: Int): TypeTheme {
            return when (value) {
                1 -> LIGHT
                2 -> DARK
                else -> throw IllegalStateException()
            }
        }
    }
}