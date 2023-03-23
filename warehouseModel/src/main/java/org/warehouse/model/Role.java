package org.warehouse.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Role implements Serializable {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
