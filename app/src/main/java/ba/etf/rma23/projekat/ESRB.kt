package ba.etf.rma23.projekat

enum class ESRB {RP,EC,E,E10,T,M,AO;

    companion object {
        private val values = values()
        operator fun get(index: Int): ESRB {
            return values[index % values.size]
        }
    }
}