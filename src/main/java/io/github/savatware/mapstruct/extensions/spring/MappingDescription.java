package io.github.savatware.mapstruct.extensions.spring;

public record MappingDescription(
    String source,
    String target,
    String dateFormat,
    String numberFormat,
    String constant,
    String expression,
    String defaultExpression,
    boolean ignore,
    String[] qualifiedByName,
    String[] conditionQualifiedByName,
    String conditionExpression,
    String[] dependsOn,
    String defaultValue
) { }

