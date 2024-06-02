package com.saga.order.constant;

public class Constant {

    public final static String URL_PATH                     = "/orders";
    public final static String URL_PATH_CLIENTS             = "/clients";
    public final static String MSG_LOG_SEARCH_ALL           = "RETURNING ALL ORDERS:";
    public final static String MSG_LOG_SEARCH_A_ORDER       = "RETURNING A ORDERS:";
    public final static String MSG_LOG_SEARCH_BY_ID         = "A ORDERS BY CLIENT ID CLIENT:";
    public final static String MSG_LOG_CANCEL_ORDER         = "CANCEL A ORDER:";
    public final static String MSG_LOG_SAVING_ORDER         = "SAVING ORDER:";
    public final static String MSG_SWAGGER_CREATE           = "Cria um pedido";
    public final static String MSG_SWAGGER_LIST_ALL         = "Lista todos os pedido";
    public final static String MSG_SWAGGER_LIST_A_ORDER     = "Lista o pedido de acordo com o ID informado";
    public final static String MSG_SWAGGER_LIST_BY_ID_C     = "Lista o pedido de acordo com o ID de cliente informado";
    public final static String MSG_SWAGGER_CANCEL_ORDER     = "Cancela o pedido de acordo com o ID de pedido informado";
    public final static String CODE_RETURN_SWAGGER_FOUND    = "302";
    public final static String CODE_RETURN_SWAGGER_SUCCESS  = "200";
    public final static String CODE_RETURN_SWAGGER_NOT_FOUND= "404";
}
