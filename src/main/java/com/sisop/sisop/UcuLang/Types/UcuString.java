package com.sisop.sisop.UcuLang.Types;

import com.sisop.sisop.UcuLang.Exceptions.InvalidTypesRuntimeException;

public class UcuString extends UcuType 
                       implements UcuAddOp,
                                  UcuAppendOp, 
                                  UcuAssignOp,
                                  UcuCopyOp,
                                  UcuGetOp, 
                                  UcuLengthOp, 
                                  UcuMulOp,
                                  UcuSetOp, 
                                  Comparable<UcuString> {

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

    /**
     * Implementación de UcuCopyOp
     * 
     * Devuelve una nueva instancia de la cadena. No 
     * comparte memoria con la cadena anterior.
     */
    @Override
    public UcuType copy() {
        return new UcuString(value.toString());
    }

    /**
     * Implementación de UcuAssignOp
     */
    @Override
    public void assign(UcuType other) {
        if (other instanceof UcuString o) {
            value = o.value;
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

    /**
     * Implementación de UcuAppendOp
     * 
     */
    @Override
    public UcuType append(UcuType other) {
        if (other instanceof UcuString str) {
            value.append(str.value);
        } else {
            value.append(other.toString());
        }
        return this;
    }

    /**
     * Implementación de UcuAddOp
     * 
     * Devuelve una nueva cadena
     */
    @Override
    public UcuType add(UcuType other) {
        return new UcuString(value.toString() + other.toString());
    }

    /**
     * Implementación de UcuMulOp
     * 
     * Dada una UcuString y un UcuNumber (n), devuelve una nueva
     * cadena con el contenido de la cadena actual repetido n veces.
     * 
     * Ejemplo (UcuLang):
     *      Código:    "Abcd" 3 * 
     *      Resultado: "AbcdAbcdAbcd"
     */
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

    /**
     * Implementación de UcuGetOp
     */
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

    /**
     * Implementación de UcuSetOp
     */
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

    /**
     * Implementación UcuLengthOp
     */
    @Override
    public int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return value.toString();
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
    public int compareTo(UcuString other) {
        if (this == other) {
            return 0;
        }
        
        return value.toString().compareTo(other.value.toString());
    }
}
