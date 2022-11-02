package com.sisop.sisop.UcuLang;

import java.util.ArrayList;

public class UcuValue {
    public Object value;

    public UcuValue(String value) {
        this.value = new StringBuilder(value);
    }

    private UcuValue(Object value) {
        this.value = value;
    }

    public UcuValue(StringBuilder value) {
        this.value = value;
    }

    public UcuValue(Double value) {
        this.value = value;
    }

    public UcuValue(ArrayList<UcuValue> value) {
        this.value = value;
    }

    public UcuValue clone() {
        if (value instanceof StringBuilder
            || value instanceof ArrayList) {
            return new UcuValue(value);
        } else if (value instanceof Double a) {
            double x = (double) a;
            return new UcuValue(x);
        }

        throw new RuntimeException("Unexpected type");
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }

    public UcuValue add(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return new UcuValue(a + b);
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof Double b) {
                a.append(b);
                return this;
            } else if (other.value instanceof StringBuilder b) {
                a.append(b);
                return this;
            }
        } else if (value instanceof ArrayList a) {
            a.add(other);
            return this;
        }

        throw new RuntimeException("Invalid operands (add): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue sub(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return new UcuValue(a - b);
            }
        }

        throw new RuntimeException("Invalid operands (sub): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue mul(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return new UcuValue(a * b);
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof Double b) {
                var cadena = new StringBuilder();
                for (int i = 0; i < b.intValue(); i++) {
                    cadena.append(a);
                }
                return new UcuValue(cadena);
            }
        } else if (value instanceof ArrayList a) {
            // if (other.value instanceof Double b) {
            //     var array = new ArrayList<UcuValue>();
            //     for (int i = 0; i < b.intValue(); i++) {
            //         array.addAll(a);
            //     }
            //     return new UcuValue(array);
            // }
        }

        throw new RuntimeException("Invalid operands (mul): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue div(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return new UcuValue(a / b);
            }
        }

        throw new RuntimeException("Invalid operands (div): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public UcuValue len() {
        if (value instanceof Double) {
            return new UcuValue(0.0);
        } else if (value instanceof StringBuilder a) {
            return new UcuValue((double) a.length());
        } else if (value instanceof ArrayList a) {
            return new UcuValue((double) a.size());
        } 

        throw new RuntimeException("Invalid operand (len): " + value.getClass().getName()); 
    }

    public UcuValue at(int index) {
        if (value instanceof StringBuilder a) {
            return new UcuValue(String.valueOf(a.charAt(index)));
        } else if (value instanceof ArrayList a) {
            return (UcuValue) a.get(index);
        } 

        throw new RuntimeException("Invalid operand (at): " + value.getClass().getName()); 
    }

    public void set(int index, UcuValue x) {
        if (value instanceof ArrayList a) {
            a.set(index, x);
            return;
        }  else if (value instanceof StringBuilder a) {
            if (x.value instanceof StringBuilder b) {
                if (b.length() > 0) {
                    a.setCharAt(index, b.charAt(0));
                } else {
                    System.out.println("B: '" + b + "' " + b.length());
                }
                return; 
            }
        } 

        throw new RuntimeException("Invalid operand (set): " + value.getClass().getName() + " and " + x.value.getClass().getName()); 
    }

    public boolean greaterThan(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return a > b;
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof StringBuilder b) {
                return a.compareTo(b) > 0;
            }
        }

        throw new RuntimeException("Invalid operands (greaterThan): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public boolean greaterThanOrEqual(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return a >= b;
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof StringBuilder b) {
                return a.compareTo(b) >= 0;
            }
        }

        throw new RuntimeException("Invalid operands (greaterThanOrEqual): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public boolean lessThan(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return a < b;
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof StringBuilder b) {
                return a.compareTo(b) < 0;
            }
        }

        throw new RuntimeException("Invalid operands (lessThan): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public boolean lessThanOrEqual(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return a <= b;
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof StringBuilder b) {
                return a.compareTo(b) <= 0;
            }
        }

        throw new RuntimeException("Invalid operands (lessThanOrEqual): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public boolean equal(UcuValue other) {
        if (value instanceof Double a) {
            if (other.value instanceof Double b) {
                return a.equals(b);
            }
        } else if (value instanceof StringBuilder a) {
            if (other.value instanceof StringBuilder b) {
                return a.toString().equals(b.toString());
            }
        } else if (value instanceof ArrayList a) {
            if (other.value instanceof ArrayList b) {
                return a.equals(b);
            }
        }

        throw new RuntimeException("Invalid operands (equal/notEqual): " + value.getClass().getName() + " and " + other.value.getClass().getName());
    }

    public boolean notEqual(UcuValue other) {
        return !equal(other);
    }
}
