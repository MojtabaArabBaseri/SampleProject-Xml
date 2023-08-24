package ir.millennium.sampleProject.presentation.utils

enum class TypeViewChildForm(val value: Int) {
    INPUT_TEXT(1),
    INPUT_NUMBER(2),
    INPUT_MULTI_LINE_TEXT(3),
    INPUT_DATE(4),
    GET_VALUE_FROM_DROP_DOWN_LIST(5),
    GET_VALUE_FROM_RADIO_BUTTON_LIST(6),
    GET_VALUE_FROM_CHECK_BOX_LIST(7),
    GET_VALUE_FROM_SUGESSTION_LIST(8),
    INPUT_STATUS(9),
    SELECT_FILE(10),
    SHOW_SEEKBAR(11),
    SHOW_MAP(12);

    companion object {
        fun create(x: Int): TypeViewChildForm {
            return when (x) {
                1 -> INPUT_TEXT
                2 -> INPUT_NUMBER
                3 -> INPUT_MULTI_LINE_TEXT
                4 -> INPUT_DATE
                5 -> GET_VALUE_FROM_DROP_DOWN_LIST
                6 -> GET_VALUE_FROM_RADIO_BUTTON_LIST
                7 -> GET_VALUE_FROM_CHECK_BOX_LIST
                8 -> GET_VALUE_FROM_SUGESSTION_LIST
                9 -> INPUT_STATUS
                10 -> SELECT_FILE
                11 -> SHOW_SEEKBAR
                12 -> SHOW_MAP
                else -> throw IllegalStateException()
            }
        }
    }
}
