package com.sisop.sisop.UcuLang.Types;

public class UcuNumber implements UcuType,
                                  UcuAddOp,
                                  UcuSubOp,
                                  UcuMulOp,
                                  UcuModOp,
                                  UcuDivOp,
                                  Comparable {
    private double number;

    public UcuNumber(double number) {
        this.number = number;
    }

    public UcuNumber(int number) {
        this.number = number;
    }

    public int intValue() {
        return (int)number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
    
    public double getValue() {
        return number;
    }

    @Override
    public UcuType div(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(number / o.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType mod(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(number % o.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType mul(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(number * o.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType sub(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(number - o.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType add(UcuType other) {
        if (other instanceof UcuNumber o) {
            return new UcuNumber(number + o.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType duplicate() {
        return new UcuNumber(number);
    }

    @Override
    public UcuType copy() {
        return duplicate();
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
            return number == o.number;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.number) ^ (Double.doubleToLongBits(this.number) >>> 32));
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof UcuNumber x) {
            return Double.compare(number, x.number);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
