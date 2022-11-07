package com.sisop.sisop.UcuLang.Types;

import com.sisop.sisop.UcuLang.Exceptions.InvalidTypesRuntimeException;

public class UcuString implements UcuType, 
                                  UcuLengthOp, 
                                  UcuAppendOp, 
                                  UcuGetOp, 
                                  UcuSetOp, 
                                  UcuMulOp,
                                  UcuAssignOp,
                                  Comparable {

    private StringBuilder value;

    public UcuString() {
        this.value = new StringBuilder();
    }

    public UcuString(String str) {
        this.value = new StringBuilder(str);
    }

    public UcuString(StringBuilder builder) {
        this.value = builder;
    }

    public StringBuilder getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public UcuType duplicate() {
        return this;
    }

    @Override
    public UcuType copy() {
        return new UcuString(value.toString());
    }

    @Override
    public UcuType mul(UcuType other) {
        if (other instanceof UcuNumber o) {
            var builder = new StringBuilder();

            for (int i = 0; i < o.intValue(); i++) {
                builder.append(value);
            }

            return new UcuString(builder);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.mul", 
                new String[][] { 
                    { UcuNumber.class.getName() },
                }, 
                new String[][] { 
                    { other.getClass().getName() },
                }
            );
        }
    }

    @Override
    public UcuType set(UcuType key, UcuType object) {
        if (key instanceof UcuNumber number && 
            object instanceof UcuString str) {
            if (str.length() > 0) {
                this.value.setCharAt(number.intValue(), str.value.charAt(0));
            }
            return this;
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.set", 
                new String[][] { 
                    { UcuNumber.class.getName() },
                    { UcuString.class.getName() }
                }, 
                new String[][] { 
                    { key.getClass().getName() },
                    { object.getClass().getName() }
                }
            );
        }
    }

    @Override
    public UcuType get(UcuType key) {
        if (key instanceof UcuNumber index) {
            var builder = new StringBuilder();
            builder.append(value.charAt(index.intValue()));
            return new UcuString(builder);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.get", 
                new String[][] { 
                    { UcuNumber.class.getName() }
                }, 
                new String[][] { 
                    { key.getClass().getName() }
                }
            );
        }
    }

    @Override
    public UcuType append(UcuType other) {
        if (other instanceof UcuString str) {
            value.append(str.value);
        } else if (other instanceof UcuNumber number) {
            value.append(number.getValue());
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.append", 
                new String[][] { 
                    { UcuString.class.getName(), UcuNumber.class.getName() }
                }, 
                new String[][] { 
                    { other.getClass().getName() }
                }
            );
        }
        return this;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public int hashCode() {
        return value.toString().hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other instanceof UcuString o) {
            return value.toString().equals(o.value.toString());
        }
        
        return false;
    }

    @Override
    public int compareTo(Object other) {
        if (this == other) {
            return 0;
        }
        
        if (other instanceof UcuString o) {
            return value.toString().compareTo(o.value.toString());
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.compareTo", 
                new String[][] { 
                    { UcuString.class.getName() }
                }, 
                new String[][] { 
                    { other.getClass().getName() }
                }
            );
        }
    }

    @Override
    public UcuType assign(UcuType other) {
        if (other instanceof UcuString o) {
            value = o.value;
            return this;
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuString.assign", 
                new String[][] { 
                    { UcuString.class.getName() }
                }, 
                new String[][] { 
                    { other.getClass().getName() }
                }
            );
        }
    }
}
