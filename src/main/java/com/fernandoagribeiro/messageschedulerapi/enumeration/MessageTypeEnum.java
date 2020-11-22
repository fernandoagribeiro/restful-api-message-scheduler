package com.fernandoagribeiro.messageschedulerapi.enumeration;

public enum MessageTypeEnum {
    Email(1),
    SMS(2),
    WhatsApp(3),
    Push(4);

    public int messageTypeValue;

    MessageTypeEnum(int value) {
        this.messageTypeValue = value;
    }

    public static boolean contains(String value) {
        for (MessageTypeEnum mt : MessageTypeEnum.values()) {
            if (mt.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
