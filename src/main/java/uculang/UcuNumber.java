package uculang;

//import com.sisop.sisop.UcuLang.Exceptions.InvalidTypesRuntimeException;

public class UcuNumber extends UcuType
                       implements UcuAddOp,
                                  UcuCopyOp,
                                  UcuSubOp,
                                  UcuMulOp,
                                  UcuModOp,
                                  UcuDivOp,
                                  UcuAssignOp,
                                  Comparable<UcuNumber> {
    private double value;

    public UcuNumber(double number) {
        this.value = number;
    }

    public UcuNumber(int number) {
        this.value = number;
    }

    public int intValue() {
        return (int)value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    public double getValue() {
        return value;
    }

    @Override
    public UcuType copy() {
        return new UcuNumber(value);
    }

    @Override
    public UcuType div(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(value / o.value);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.div", 
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
    public UcuType mod(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(value % o.value);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.mod", 
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
    public UcuType mul(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(value * o.value);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.mul", 
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
    public UcuType sub(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(value - o.value);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.sub", 
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
    public UcuType add(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(value + o.value);
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.add", 
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
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other instanceof UcuNumber o) {
            return value == o.value;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public int compareTo(UcuNumber other) {
        return Double.compare(value, other.value);
    }

    @Override
    public void assign(UcuType other) {
        if (other instanceof UcuNumber o) {
            value = o.value;
        } else {
            throw new InvalidTypesRuntimeException(
                "UcuNumber.assign", 
                new String[][] { 
                    { UcuNumber.class.getName() },
                }, 
                new String[][] { 
                    { other.getClass().getName() },
                }
            );
        }
    }
}
