package io.github.savatware.mapstruct.addons.spring.processor.pageable;

public class PageableMapperSourceWriter {

    /*

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.List;

public class Temp {

    private List<String> mappings;

    public Pageable mapToSource(Pageable pageable) {
        var convertedSort = mapToSource(pageable.getSort());
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), convertedSort);
    }

    public Sort mapToSource(Sort sort) {
        var convertedOrders = sort.stream().map(this::mapToSource).toList();

        return Sort.by(convertedOrders);
    }

    private Order mapToSource(Order order) {
        var convertedName = mapToSource(order.getProperty());
        return new Order(order.getDirection(), convertedName, order.isIgnoreCase(), order.getNullHandling());
    }

    private String mapToSource(String targetPropertyName) {
        return mappings.stream()
                .filter(o -> o.equals(targetPropertyName))
                .findFirst()
                .map(o -> o.toLowerCase())
                .orElse(targetPropertyName); // if no specific mapping then assume identical name in source and target
    }

}

     */

}
