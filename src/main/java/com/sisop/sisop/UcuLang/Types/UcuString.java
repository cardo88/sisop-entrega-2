package com.sisop.sisop.UcuLang.Types;

public class UcuString implements UcuType, 
                                  UcuLengthOp, 
                                  UcuAppendOp, 
                                  UcuGetOp, 
                                  UcuSetOp, 
                                  UcuMulOp,
                                  Comparable {
    private StringBuilder string;

    public UcuString() {
        this.string = new StringBuilder();
    }

    public UcuString(String str) {
        this.string = new StringBuilder(str);
    }

    public UcuString(StringBuilder builder) {
        this.string = builder;
    }
    
    @Override
    public String toString() {
        return string.toString();
    }

    @Override
    public UcuType duplicate() {
        return this;
    }

    @Override
    public UcuType mul(UcuType other) {
        if (other instanceof UcuNumber o) {
            var builder = new StringBuilder(string.length() * o.intValue());
            for (int i = 0; i < o.intValue(); i++) {
                builder.append(string);
            }
            return new UcuString(builder);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType set(UcuType key, UcuType value) {
        if (key instanceof UcuNumber number && 
            value instanceof UcuString str) {
            if (str.length() > 0) {
                string.setCharAt(number.intValue(), str.string.charAt(0));
            }
            return this;
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType get(UcuType key) {
        if (key instanceof UcuNumber index) {
            var builder = new StringBuilder();
            builder.append(string.charAt(index.intValue()));
            return new UcuString(builder);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType append(UcuType other) {
        if (other instanceof UcuString str) {
            string.append(str.string);
        } else if (other instanceof UcuNumber number) {
            string.append(number.getValue());
        } else {
            throw new RuntimeException("FIXME");
        }
        return this;
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public UcuType copy() {
        return new UcuString(string.toString());
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other instanceof UcuString o) {
            return string.toString().equals(o.string.toString());
        }
        
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        }
        
        if (o instanceof UcuString x) {
            return string.toString().compareTo(x.string.toString());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
