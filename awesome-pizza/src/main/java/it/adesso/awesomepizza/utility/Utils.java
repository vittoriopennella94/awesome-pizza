package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.enums.OrderStateEnum;

public class Utils {
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
}
