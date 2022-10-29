package com.sisop.sisop.UcuLang;


public class UcuValue {
    public final Object value;

    public UcuValue(String value) {
        this.value = value;
    }

    public UcuValue(Double value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "UcuValue(" + this.value.toString() + ")";
    }

    public UcuValue add(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return new UcuValue(a + b);
        } else if (value instanceof String a && other.value instanceof String b) {
            return new UcuValue(a + b);
        } else if (value instanceof String a && other.value instanceof Double b) {
            return new UcuValue(a + b);
        } else if (value instanceof Double a && other.value instanceof String b) {
            return new UcuValue(a + b);
        } else {
            return null;
        } 
    }

    public UcuValue sub(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return new UcuValue(a - b);
        } else {
            return null;
        } 
    }

    public UcuValue mul(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return new UcuValue(a * b);
        } else {
            return null;
        } 
    }

    public UcuValue div(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return new UcuValue(a / b);
        } else {
            return null;
        } 
    }

    public boolean greaterThan(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return a > b;
        } else if (value instanceof String a && other.value instanceof String b) {
            return a.compareTo(b) > 0;
        } else {
            return false;
        } 
    }

    public boolean greaterThanOrEqual(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return a >= b;
        } else if (value instanceof String a && other.value instanceof String b) {
            return a.compareTo(b) >= 0;
        } else {
            return false;
        } 
    }

    public boolean lessThan(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return a < b;
        } else if (value instanceof String a && other.value instanceof String b) {
            return a.compareTo(b) < 0;
        } else {
            return false;
        } 
    }

    public boolean lessThanOrEqual(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return a <= b;
        } else if (value instanceof String a && other.value instanceof String b) {
            return a.compareTo(b) < 0;
        } else {
            return false;
        } 
    }

    public boolean equal(UcuValue other) {
        if (value instanceof Double a && other.value instanceof Double b) {
            return a.equals(b);
        } else if (value instanceof String a && other.value instanceof String b) {
            return a.compareTo(b) == 0;
        } else {
            return false;
        } 
    }
}
