package exercicios;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Versão final corrigida com princípios de programação funcional modernos (Java 25).
 *
 * @author Manoel Campos
 */
public class CodigoFuncionalRuim {
    private final List<List<String>> listaCidadesPorLetraInicial = List.of(
            List.of("Aracajú", "Abreulândia"),
            List.of("Brejinho de Nazaré", "Bom Jesus do Tocantins"),
            List.of("Brasília", "Belém", "Belo Horizonte")
    );

    private final List<List<Integer>> distanciasPoligonos = List.of(
            List.of(10, 10, 10),
            List.of(25, 25, 25),
            List.of(20, 10, 20, 10),
            List.of(30, 30, 30, 30, 30),
            List.of(10, 10, 15, 10, 15)
    );

    public CodigoFuncionalRuim() {
        codigoRuim1();
        correcao1();

        System.out.println();
        codigoRuim2();
        codigoRuim3();
        correcao2and3();

        System.out.println();
        codigoRuim4();
        correcao4();

        System.out.println();
        codigoRuim5();
        correcao5();

        System.out.println();
        codigoRuim6_1();
        codigoRuim6_2();
        correcao6();

        System.out.println();
        codigoRuim7();
        correcao7();

        System.out.println();
        codigoRuim8();
    }

    public static void main(String[] args) {
        new CodigoFuncionalRuim();
    }

    private void codigoRuim1() {
        var cidadesMap = new HashMap<Character, List<String>>();
        for (List<String> listaCidades : listaCidadesPorLetraInicial) {
            cidadesMap.put(listaCidades.getFirst().charAt(0), listaCidades);
        }

        var totalCidadesMap =
            cidadesMap
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, p -> p.getValue().size()));
        System.out.println("Total de cidades por letra: " + totalCidadesMap);
    }

    /**
     * Correção 1: Usa toMap diretamente com função de soma para duplicatas.
     */
    private void correcao1() {
        var totalCidadesMap = listaCidadesPorLetraInicial.stream()
                .filter(lista -> !lista.isEmpty())
                .collect(toMap(
                        lista -> lista.getFirst().charAt(0),
                        List::size,
                        Integer::sum 
                ));
        System.out.println("Correção 1 - Total de cidades por letra: " + totalCidadesMap);
    }

    private void codigoRuim2() {
        var totalCidadesList =
            IntStream
                .range(0, listaCidadesPorLetraInicial.size())
                .mapToObj(i -> listaCidadesPorLetraInicial.get(i).size())
                .toList();

        System.out.println("Total de cidades em cada grupo: " + totalCidadesList);
    }

    private void codigoRuim3() {
        var totalCidadesList = new ArrayList<Integer>();
        listaCidadesPorLetraInicial
                .forEach(cidades -> totalCidadesList.add(cidades.size()));

        System.out.println("Total de cidades em cada grupo: " + totalCidadesList);
    }

    /**
     * Correção 2 e 3: Simplifica com map(List::size).
     */
    private void correcao2and3() {
        var totalCidadesList = listaCidadesPorLetraInicial.stream()
                .map(List::size)
                .toList();
        System.out.println("Correção 2 e 3 - Total de cidades em cada grupo: " + totalCidadesList);
    }

    private void codigoRuim4() {
        var nomePoligonos =
            distanciasPoligonos
              .stream()
              .map(distancias -> {
                 switch (distancias.size()) {
                   case 3: return "Triângulo";
                   case 4: return "Quadrilátero";
                   case 5: return "Pentágono";
                   case 6: return "Hexágono";
                   default: return "Polígono de %d lados".formatted(distancias.size());
                 }
              })
              .toList();

        System.out.println("Tipos de polígonos: " + nomePoligonos);
    }

    /**
     * Correção 4: Usa Switch Expression e extração de método.
     */
    private void correcao4() {
        var nomePoligonos = distanciasPoligonos.stream()
                .map(this::getTipoPoligono)
                .toList();
        System.out.println("Correção 4 - Tipos de polígonos: " + nomePoligonos);
    }

    private String getTipoPoligono(List<Integer> lados) {
        return switch (lados.size()) {
            case 3 -> "Triângulo";
            case 4 -> "Quadrilátero";
            case 5 -> "Pentágono";
            case 6 -> "Hexágono";
            default -> "Polígono de %d lados".formatted(lados.size());
        };
    }

    private void codigoRuim5() {
        var list =
              Stream.of(distanciasPoligonos)
                    .filter(distancias -> distancias.size() >= 4)
                    .toList();
        System.out.println("Polígonos com mais de 3 lados: " + list);
    }

    /**
     * Correção 5: Usa .stream() em vez de Stream.of().
     */
    private void correcao5() {
        var list = distanciasPoligonos.stream()
                .filter(distancias -> distancias.size() >= 4)
                .toList();
        System.out.println("Correção 5 - Polígonos com mais de 3 lados: " + list);
    }

    private void codigoRuim6_1() {
        var perimetrosList =
                distanciasPoligonos.stream().flatMap(distancias -> {
                    var perimetro = 0;
                    for (Integer distancia : distancias) {
                        perimetro += distancia;
                    }
                    return Stream.of(perimetro);
                })
                                   .toList();

                    System.out.println("Perímetros: " + perimetrosList);
    }

    private void codigoRuim6_2() {
        var perimetrosList =
                distanciasPoligonos
                    .stream()
                    .flatMap(distancias -> {
                        var perimetro = distancias.stream().mapToInt(d -> d).sum();
                        return Stream.of(perimetro);
                    }).toList();

        System.out.println("Perímetros: " + perimetrosList);
    }

    /**
     * Correção 6: Usa reduce para somar as distâncias.
     */
    private void correcao6() {
        var perimetrosList = distanciasPoligonos.stream()
                .flatMap(distancias -> Stream.of(distancias.stream().reduce(0, Integer::sum)))
                .toList();
        System.out.println("Correção 6 - Perímetros: " + perimetrosList);
    }

    private void codigoRuim7() {
        final Map<String, Integer> cidadesPorEstado = Map.of(
                "BA", 417, "CE", 184, "DF", 1, "ES", 78, "GO", 246,
                "MG", 853, "PA", 144, "RS", 497, "SP", 645, "TO", 139
        );

        var map = cidadesPorEstado
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 300)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Estados com mais de 300 cidades:");
        map.forEach((estado, cidades) -> System.out.printf("%s: %d cidades%n", estado, cidades));
    }

    /**
     * Correção 7: Usa TreeMap para garantir ordenação.
     */
    private void correcao7() {
        final Map<String, Integer> cidadesPorEstado = Map.of(
                "BA", 417, "CE", 184, "DF", 1, "ES", 78, "GO", 246,
                "MG", 853, "PA", 144, "RS", 497, "SP", 645, "TO", 139
        );

        var mapOrdenado = cidadesPorEstado.entrySet().stream()
                .filter(e -> e.getValue() > 300)
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        TreeMap::new
                ));

        System.out.println("Correção 7 - Estados com mais de 300 cidades (Ordenados):");
        mapOrdenado.forEach((estado, cidades) -> System.out.printf("%s: %d cidades%n", estado, cidades));
    }

    /**
     * Correção 8: Usa .toList() direto na Stream.
     */
    private void codigoRuim8() {
        var lista = listaCidadesPorLetraInicial
                        .stream()
                        .flatMap(List::stream)
                        .toList();

        System.out.println("Correção 8 - Lista de todas as cidades (Imutável): " + lista);
    }
}
