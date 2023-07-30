package com.sabrina.studynow.base;

import lombok.*;

@Builder @Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Range {
    protected Double min;
    protected Double max;
    protected Double step;
    protected Double value;
}
