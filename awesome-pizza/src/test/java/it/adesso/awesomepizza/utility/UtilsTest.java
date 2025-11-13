package it.adesso.awesomepizza.utility;

import it.adesso.awesomepizza.enums.OrderStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void checkIfChangeStateOkTest_From_IN_ATTESA_to_IN_PREPARAZIONE_Ok() {
        Long stateIdFrom = OrderStateEnum.IN_ATTESA.getId();
        Long stateIdTo = OrderStateEnum.IN_PREPARAZIONE.getId();

        Assertions.assertTrue(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_ATTESA_to_ANNULLATO_Ok() {
        Long stateIdFrom = OrderStateEnum.IN_ATTESA.getId();
        Long stateIdTo = OrderStateEnum.ANNULLATO.getId();

        Assertions.assertTrue(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_ATTESA_to_IN_CONSEGNA_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_ATTESA.getId();
        Long stateIdTo = OrderStateEnum.IN_CONSEGNA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_ATTESA_to_CONSEGNATO_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_ATTESA.getId();
        Long stateIdTo = OrderStateEnum.CONSEGNATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_PREPARAZIONE_to_IN_CONSEGNA_Ok() {
        Long stateIdFrom = OrderStateEnum.IN_PREPARAZIONE.getId();
        Long stateIdTo = OrderStateEnum.IN_CONSEGNA.getId();

        Assertions.assertTrue(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_PREPARAZIONE_to_ANNULLATO_Ok() {
        Long stateIdFrom = OrderStateEnum.IN_PREPARAZIONE.getId();
        Long stateIdTo = OrderStateEnum.ANNULLATO.getId();

        Assertions.assertTrue(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_PREPARAZIONE_to_IN_ATTESA_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_PREPARAZIONE.getId();
        Long stateIdTo = OrderStateEnum.IN_ATTESA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_PREPARAZIONE_to_CONSEGNATO_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_PREPARAZIONE.getId();
        Long stateIdTo = OrderStateEnum.CONSEGNATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_CONSEGNA_to_CONSEGNATO_Ok() {
        Long stateIdFrom = OrderStateEnum.IN_CONSEGNA.getId();
        Long stateIdTo = OrderStateEnum.CONSEGNATO.getId();

        Assertions.assertTrue(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_CONSEGNA_to_IN_PREPARAZIONE_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_CONSEGNA.getId();
        Long stateIdTo = OrderStateEnum.IN_PREPARAZIONE.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_CONSEGNA_to_IN_ATTESA_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_CONSEGNA.getId();
        Long stateIdTo = OrderStateEnum.IN_ATTESA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_CONSEGNA_to_ANNULLATO_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_CONSEGNA.getId();
        Long stateIdTo = OrderStateEnum.ANNULLATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_ANNULLATO_to_IN_ATTESA_Ko() {
        Long stateIdFrom = OrderStateEnum.ANNULLATO.getId();
        Long stateIdTo = OrderStateEnum.IN_ATTESA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_ANNULLATO_to_IN_PREPARAZIONE_Ko() {
        Long stateIdFrom = OrderStateEnum.ANNULLATO.getId();
        Long stateIdTo = OrderStateEnum.IN_PREPARAZIONE.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_ANNULLATO_to_IN_CONSEGNA_Ko() {
        Long stateIdFrom = OrderStateEnum.ANNULLATO.getId();
        Long stateIdTo = OrderStateEnum.IN_CONSEGNA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_ANNULLATO_to_CONSEGNATO_Ko() {
        Long stateIdFrom = OrderStateEnum.ANNULLATO.getId();
        Long stateIdTo = OrderStateEnum.CONSEGNATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_CONSEGNATO_to_IN_CONSEGNA_Ko() {
        Long stateIdFrom = OrderStateEnum.CONSEGNATO.getId();
        Long stateIdTo = OrderStateEnum.IN_CONSEGNA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_CONSEGNATO_to_IN_PREPARAZIONE_Ko() {
        Long stateIdFrom = OrderStateEnum.CONSEGNATO.getId();
        Long stateIdTo = OrderStateEnum.IN_PREPARAZIONE.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_CONSEGNATO_to_IN_ATTESA_Ko() {
        Long stateIdFrom = OrderStateEnum.CONSEGNATO.getId();
        Long stateIdTo = OrderStateEnum.IN_ATTESA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_CONSEGNATO_to_ANNULLATO_Ko() {
        Long stateIdFrom = OrderStateEnum.CONSEGNATO.getId();
        Long stateIdTo = OrderStateEnum.ANNULLATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_ATTESA_to_IN_ATTESA_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_ATTESA.getId();
        Long stateIdTo = OrderStateEnum.IN_ATTESA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_PREPARAZIONE_to_IN_PREPARAZIONE_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_PREPARAZIONE.getId();
        Long stateIdTo = OrderStateEnum.IN_PREPARAZIONE.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_IN_CONSEGNA_to_IN_CONSEGNA_Ko() {
        Long stateIdFrom = OrderStateEnum.IN_CONSEGNA.getId();
        Long stateIdTo = OrderStateEnum.IN_CONSEGNA.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_CONSEGNATO_to_CONSEGNATO_Ko() {
        Long stateIdFrom = OrderStateEnum.CONSEGNATO.getId();
        Long stateIdTo = OrderStateEnum.CONSEGNATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }

    @Test
    public void checkIfChangeStateOkTest_From_ANNULLATO_to_ANNULLATO_Ko() {
        Long stateIdFrom = OrderStateEnum.ANNULLATO.getId();
        Long stateIdTo = OrderStateEnum.ANNULLATO.getId();

        Assertions.assertFalse(Utils.checkIfChangeStateOk(stateIdFrom, stateIdTo));
    }


}
