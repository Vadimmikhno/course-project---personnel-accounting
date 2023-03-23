package org.warehouse.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Warehouse implements Serializable {
    private Long id;
    private String name;
    private Double squar;
    private Long numberOfEmployees;
    private List<Item> items;
    private List<Category> categories;

    @Override
    public String toString() {
        return name;
    }
}
