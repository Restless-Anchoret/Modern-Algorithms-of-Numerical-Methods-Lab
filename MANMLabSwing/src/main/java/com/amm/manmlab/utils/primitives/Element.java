package com.amm.manmlab.utils.primitives;

import java.util.Objects;

public class Element {

    private final int i, j, k;

    public Element(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getK() {
        return k;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Element{");
        sb.append("i=").append(i);
        sb.append(", j=").append(j);
        sb.append(", k=").append(k);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return i == element.i &&
                j == element.j &&
                k == element.k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, k);
    }
}
