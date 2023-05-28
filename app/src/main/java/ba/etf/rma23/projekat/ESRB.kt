package ba.etf.rma23.projekat

enum class ESRB {Three,Seven,Twelve,Sixteen,Eighteen,RP,EC,E,E10,T,M,AO,CERO_A,CERO_B,CERO_C,CERO_D,CERO_Z,USK_0,USK_6,USK_12,USK_16,USK_18,GRAC_ALL,GRAC_Twelve,GRAC_Fifteen,GRAC_Eighteen,GRAC_TESTING,CLASS_IND_L,CLASS_IND_Ten,CLASS_IND_Twelve,CLASS_IND_Fourteen,CLASS_IND_Sixteen,CLASS_IND_Eighteen,ACB_G,ACB_PG,ACB_M,ACB_MA15,ACB_R18,
    ACB_RC;

    companion object {
        private val values = values()
        operator fun get(index: Int): ESRB {
            return values[index % values.size]
        }
    }
}