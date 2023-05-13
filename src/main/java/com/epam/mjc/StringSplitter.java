package com.epam.mjc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        String delimiterRegex = delimiters.stream()
                .map(Pattern::quote) // escape any special regex characters
                .collect(Collectors.joining("|")); // join delimiters with OR operator

        return Arrays.stream(source.split(delimiterRegex))
                .filter(s -> !s.isEmpty()) // filter out empty strings
                .collect(Collectors.toList());
    }
}
