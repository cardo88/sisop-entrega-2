package com.sisop.sisop.UcuLang;

import java.util.LinkedList;

public class UcuValue {
    public final Object value;

    public UcuValue(String value) {
        this.value = value;
    }

    public UcuValue(Double value) {
        this.value = value;
    }

    public UcuValue(LinkedList<UcuValue> value) {
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
        } else if (value instanceof LinkedList a && !(other.value instanceof LinkedList)) {
            a.add(other);
            return this;
        } else if (value instanceof LinkedList a && other.value instanceof LinkedList b) {
            a.addAll(b);
            return this;
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

    public UcuValue len() {
        if (value instanceof Double) {
            return new UcuValue(0.0);
        } else if (value instanceof String a) {
            return new UcuValue((double) a.length());
        } else if (value instanceof LinkedList a) {
            return new UcuValue((double) a.size());
        } else {
            return null;
        } 
    }

    public UcuValue at(int index) {
        if (value instanceof Double) {
            return this;
        } else if (value instanceof String a) {
            return new UcuValue("" + a.charAt(index));
        } else if (value instanceof LinkedList a) {
            return (UcuValue) a.get(index);
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
        } else if (value instanceof LinkedList a && other.value instanceof LinkedList b) {
            return a.equals(b);
        } else {
            return false;
        } 
    }
}
