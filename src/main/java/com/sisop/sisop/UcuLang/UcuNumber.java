package com.sisop.sisop.UcuLang;

public class UcuNumber implements Comparable<UcuNumber> {
    public double value;

    public UcuNumber(int number) {
        value = (double) number;
    }

    public UcuNumber(double number) {
        value = number;
    }

    public int intValue() {
        return (int) value;
    }

    public UcuNumber deepCopy() {
        return new UcuNumber(value);
    }

    public UcuNumber add(UcuNumber o) {
        return new UcuNumber(value + o.value);
    }

    public UcuNumber sub(UcuNumber o) {
        return new UcuNumber(value - o.value);
    }

    public UcuNumber mul(UcuNumber o) {
        return new UcuNumber(value * o.value);
    }

    public UcuNumber div(UcuNumber o) {
        return new UcuNumber(value / o.value);
    }

    public UcuNumber mod(UcuNumber o) {
        return new UcuNumber(value % o.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(UcuNumber o) {
        return Double.compare(value, o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof UcuNumber)) {
            return false;
        }
         
        return value == ((UcuNumber) o).value;
    }
}
