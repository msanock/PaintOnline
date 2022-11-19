package connection.protocol;

public enum Protocol {
    //-- BOTH --//
    CREATE_PIORO,    //  ID_PIORA,
    MOVE_TO_POINT,    // ID_PIORA, pozycja piura
    SET_TYPE, // ID_PIORA , tryb rysowania
    DELETE_PIORO,    //  ID_PIORA,

    //-- FROM_SERVER  --//
    HELLO_MESSAGE,
    ALL_INFO, // cala historia servera

    //-- FROM_CLIENT --//
    SUBSCRIBE_FOR,          // SubscribeRequest  (subscribe or unsubscribe from world id)

}
