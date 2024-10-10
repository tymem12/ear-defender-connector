package com.eardefender.model;

import lombok.Data;

@Data
public class InputParams implements Cloneable {
    private String startingPoint;
    private Integer depth;
    private Integer maxFiles;
    private String model;

    @Override
    public InputParams clone() {
        try {
            return (InputParams) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
