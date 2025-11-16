package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.enums.OrderStateEnum;

import java.util.IllegalFormatException;


/**
 * Utility class
 */
public class Utils {


    /**
     * Static function to check for correct state change
     *
     * <p>The possible state transition are</p>
     * <ol>
     *     <li>From IN_ATTESA to IN_PREPARAZIONE or ANNULLATO</li>
     *     <li>From IN_PREPARAZIONE to IN_CONSEGNA or ANNULLATO</li>
     *     <li>FROM IN_CONSEGNA to CONSEGNATO</li>
     * </ol>
     *
     *
     * @param stateIdFrom Current order status
     * @param stateIdTo New order status
     * @return true if the state transition is possible, false if the state transition is NOT possible
     */
    public static boolean checkIfChangeStateOk(Long stateIdFrom, Long stateIdTo) {
        boolean result = false;

        if(stateIdFrom != null && stateIdTo != null &&
                OrderStateEnum.findById(stateIdFrom) != null && OrderStateEnum.findById(stateIdTo) != null){

            if(OrderStateEnum.IN_ATTESA.getId().equals(stateIdFrom)){
                result = OrderStateEnum.IN_PREPARAZIONE.getId().equals(stateIdTo) || OrderStateEnum.ANNULLATO.getId().equals(stateIdTo);
            }else if(OrderStateEnum.IN_PREPARAZIONE.getId().equals(stateIdFrom)){
                result = OrderStateEnum.IN_CONSEGNA.getId().equals(stateIdTo) || OrderStateEnum.ANNULLATO.getId().equals(stateIdTo);
            }else if(OrderStateEnum.IN_CONSEGNA.getId().equals(stateIdFrom)){
                result = OrderStateEnum.CONSEGNATO.getId().equals(stateIdTo);
            }
        }

        return  result;
    }

    /**
     * Formats a message string with the provided arguments.
     *
     * @param message the message template with format specifiers (e.g., %s, %d)
     * @param args the arguments to replace the placeholders in the message
     * @return the formatted message string
     * @throws IllegalFormatException if the format string is invalid or the arguments don't match
     * @see String#format(String, Object...)
     */
    public static String formatMessage(String message, Object ... args) {
        return String.format(message, args);
    }
}
