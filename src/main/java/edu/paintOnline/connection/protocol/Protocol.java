package edu.paintOnline.connection.protocol;

public enum Protocol {
    //-- BOTH --//
    CREATE_PEN,    //  ID_OF_PEN
    MOVE_TO_POINT,    // ID_OF_PEN, POSITION
    SET_TYPE, // ID_OF_PEN , ACTION_TYPE
    DELETE_PEN,    //  ID_OF_PEN,

    //-- FROM_SERVER  --//
    HELLO_MESSAGE,
    ALL_INFO, // get whole state of server

    //-- FROM_CLIENT --//
    SUBSCRIBE_FOR,          // SubscribeRequest  (subscribe or unsubscribe from world id)

}
