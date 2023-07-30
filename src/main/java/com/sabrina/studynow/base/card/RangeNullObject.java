package com.sabrina.studynow.base.card;

import com.sabrina.studynow.base.Range;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public final class RangeNullObject extends Range {
    public RangeNullObject() {
        this.min = 2500.00;
        this.max = 10000.00;
        this.step = 10.0;
        this.value = 5000.0;
    }
}
