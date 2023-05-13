package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String notArgs = signatureString.substring(0, signatureString.indexOf("("));
        String args = signatureString.substring(signatureString.indexOf("(") + 1,
                signatureString.lastIndexOf(")"));

        //1. tokenize access, return type, method name with " "
        StringTokenizer notArgumentsTokenizer = new StringTokenizer(notArgs, " ");
        List<String> notArguments = new ArrayList<>();
        while (notArgumentsTokenizer.hasMoreTokens()) {
            notArguments.add(notArgumentsTokenizer.nextToken());
        }

        //2. tokenize each argument pair with "," then fill list of argument objects
        StringTokenizer argumentsTokenizer = new StringTokenizer(args, ",");
        List<MethodSignature.Argument> arguments = new ArrayList<>(argumentsTokenizer.countTokens());
        List<String> oneArgument = new ArrayList<>();
        while (argumentsTokenizer.hasMoreTokens()) {
            oneArgument.clear();
            oneArgument.addAll(Arrays.stream(argumentsTokenizer.nextToken().split(" "))
                    .filter(s -> !s.isEmpty()).collect(Collectors.toList()));
            arguments.add(new MethodSignature.Argument(oneArgument.get(0), oneArgument.get(1)));
        }

        //3. create new MethodSignature with name, list of arguments and set return type and access if it is presented
        MethodSignature methodSignature = null;
        if (notArguments.size() == 3) {
            methodSignature = new MethodSignature(notArguments.get(2), arguments);
            methodSignature.setAccessModifier(notArguments.get(0));
            methodSignature.setReturnType(notArguments.get(1));
        } else if(notArguments.size() == 2) {
            methodSignature = new MethodSignature(notArguments.get(1), arguments);
            methodSignature.setReturnType(notArguments.get(0));
        }

        return methodSignature;
    }
}
