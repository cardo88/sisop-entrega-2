package com.sisop.sisop.UcuLang;

public class UcuString implements Comparable<UcuString> {
    private StringBuilder value;

    public UcuString() {
        value = new StringBuilder();
    }

    public UcuString(String s) {
        value = new StringBuilder(s);
    }

    public UcuString(StringBuilder builder) {
        value = builder;
    }

    public UcuString deepCopy() {
        return new UcuString(value.toString());
    }

    public UcuString add(UcuString o) {
        return new UcuString(value.toString() + o.toString());
    }

    public UcuString mul(UcuNumber o) {
        if (o.intValue() > 0) {
            var builder = new StringBuilder(value.length() * o.intValue());
            for (int i = 0; i < o.intValue(); i++) {
                builder.append(value);
            }
            return new UcuString(builder);
        }
        return new UcuString();
    }

    public int length() {
        return value.length();
    }

    public UcuString get(int index) {
        var builder = new StringBuilder();
        builder.append(value.charAt(index));
        return new UcuString(builder);
    }

    public void set(int index, UcuString x) {
        if (x.length() > 0) {
            value.setCharAt(index, x.value.charAt(0));
        }
    }

    public void append(UcuString o) {
        value.append(o.value);
    }

    public void append(UcuNumber o) {
        value.append(String.valueOf(o.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(UcuString o) {
        return value.toString().compareTo(o.value.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof UcuString)) {
            return false;
        }
         
        return value.toString().equals(((UcuString) o).value.toString());
    }
}
