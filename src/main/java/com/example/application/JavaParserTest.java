package com.example.application;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JavaParserTest {

    public static void main(String[] args) throws IOException {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_19);
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver(false));
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        parserConfiguration.setSymbolResolver(symbolSolver);
        StaticJavaParser.setConfiguration(parserConfiguration);
        String classPath = System.getProperty("java.class.path");
        for (String entry : classPath.split(File.pathSeparator)) {
            if(entry.endsWith(".jar")) {
                try{
                    combinedTypeSolver.add(new JarTypeSolver(entry));
                }catch (IOException ex){
                    System.out.println("Can't find class path: " + entry);
                }

            }
        }
        Path path = Path.of("./src/main/java/com/example/application/examplefeature/ui/TaskListView.java");
        Path normalized = path.toAbsolutePath().normalize();
        CompilationUnit cu = StaticJavaParser.parse(normalized);
        List<MethodCallExpr> all = cu.findAll(MethodCallExpr.class);
        for (MethodCallExpr methodCallExpr : all) {
            methodCallExpr.resolve();
        }
    }
}
