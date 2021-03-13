package com.gzy.quickapi.ps5.enums;

import lombok.Getter;

public enum PS5TypeEnum {
    OPTICAL_DRIVE(0, "PS5光驱版"),
    DIGITAL_EDITION(1, "PS5数字版");

    private final Integer typeCode;

    private final String typeName;

    PS5TypeEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }
}
