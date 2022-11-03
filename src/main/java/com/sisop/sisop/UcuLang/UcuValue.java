package com.sisop.sisop.UcuLang;

public class UcuValue {
    public Object value;

    public UcuValue(UcuString v) {
        this.value = v;
    }

    public UcuValue(UcuNumber v) {
        this.value = v;
    }

    public UcuValue(UcuList v) {
        this.value = v;
    }

    public UcuValue deepCopy() {
        if (value instanceof UcuNumber a) {
            return new UcuValue(a.deepCopy());
        } else if (value instanceof UcuString a) {
            return new UcuValue(a.deepCopy());
        } else if (value instanceof UcuList a) {
            return new UcuValue(a.deepCopy());
        }
        throw new RuntimeException();
    }

    public UcuValue duplicate() {
        if (value instanceof UcuNumber a) {
            return new UcuValue(new UcuNumber(a.value));
        } else if (value instanceof UcuString || value instanceof UcuList) {
            return this;
        }
        throw new RuntimeException();
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }

    public UcuNumber asNumber() {
        return (UcuNumber) value;
    }

    public void append(UcuValue other) {
        if (value instanceof UcuString a) {
            if (other.value instanceof UcuString b) {
                a.append(b);
                return;
            } else if (other.value instanceof UcuNumber b) {
                a.append(b);
                return;
            }
        } else if (value instanceof UcuList a) {
            a.append(other);
            return;
        }

        throw new RuntimeException("Invalid operands (append): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue add(UcuValue other) {
        if (value instanceof UcuNumber a) {
            if (other.value instanceof UcuNumber b) {
                return new UcuValue(a.add(b));
            }
        } else if (value instanceof UcuString a) {
            if (other.value instanceof UcuString b) {
                return new UcuValue(a.add(b));
            }
        }

        throw new RuntimeException("Invalid operands (add): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue sub(UcuValue other) {
        if (value instanceof UcuNumber a) {
            if (other.value instanceof UcuNumber b) {
                return new UcuValue(a.sub(b));
            }
        }

        throw new RuntimeException("Invalid operands (sub): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue mul(UcuValue other) {
        if (value instanceof UcuNumber a) {
            if (other.value instanceof UcuNumber b) {
                return new UcuValue(a.mul(b));
            }
        } else if (value instanceof UcuString a) {
            if (other.value instanceof UcuNumber b) {
                return new UcuValue(a.mul(b));
            }
        }

        throw new RuntimeException("Invalid operands (mul): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue div(UcuValue other) {
        if (value instanceof UcuNumber a) {
            if (other.value instanceof UcuNumber b) {
                return new UcuValue(a.div(b));
            }
        }


        throw new RuntimeException("Invalid operands (div): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public int length() {
        if (value instanceof UcuString a) {
            return a.length();
        } else if (value instanceof UcuList a) {
            return a.length();
        } 

        throw new RuntimeException("Invalid operand (len): " + value.getClass().getName()); 
    }

    public UcuValue get(int index) {
        if (value instanceof UcuString a) {
            return new UcuValue(a.get(index));
        } else if (value instanceof UcuList a) {
            return a.get(index);
        } 

        throw new RuntimeException("Invalid operand (at): " + value.getClass().getName()); 
    }

    public void set(int index, UcuValue x) {
        if (value instanceof UcuList a) {
            a.set(index, x);
            return;
        }  else if (value instanceof UcuString a) {
            if (x.value instanceof UcuString b) {
                a.set(index, b);
                return;
            }
        } 

        throw new RuntimeException("Invalid operand (set): " + value.getClass().getName() + " and " + x.value.getClass().getName()); 
    }

    public int compareTo(UcuValue o) {
        if (value instanceof UcuNumber a) {
            if (o.value instanceof UcuNumber b) {
                return a.compareTo(b);
            }
        } else if (value instanceof UcuString a) {
            if (o.value instanceof UcuString b) {
                return a.compareTo(b);
            }
        }

        throw new RuntimeException("Invalid operand (compare): " + value.getClass().getName() + " and " + o.value.getClass().getName()); 
    }

    public boolean equals(UcuValue o) {
        return value.equals(o.value);
    }
}
