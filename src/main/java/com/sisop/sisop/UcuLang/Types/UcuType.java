package com.sisop.sisop.UcuLang.Types;

/**
 * Clase base para todos los tipos de datos de UcuLang
 */
public abstract class UcuType { 

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
