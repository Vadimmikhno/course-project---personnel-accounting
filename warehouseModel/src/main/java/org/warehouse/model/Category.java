package org.warehouse.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Category implements Serializable {
    private Long id;
    private List<Item> items;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
