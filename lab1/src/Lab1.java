import java.util.*;
import java.util.stream.Collectors;

public class Lab1 {

    public double round (double d) {
        return Double.parseDouble(String.format("%.2f",d));
    }

    public Map<Double, Double> initializeSetA(int size) {
        Map<Double, Double> map = new HashMap<>(size);
        for (double i = 0; i <= size; i++) {
            map.put(i, round(i / size));
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//        map.put(8.0, 0.1);
//        map.put(3.0, 0.8);
//        map.put(2.0, 0.9);
//        return map;
    }

    public Map<Double, Double> initializeSetB(int size) {
        Map<Double, Double> map = new HashMap<>(size);
        for (double i = 0; i <= size; i++) {
            map.put(0.5 * i, round(i / (2 * size)));
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//        map.put(1.0, 0.7);
//        map.put(2.0, 0.4);
//        return map;
    }

    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
        FuzzyNumber fuzzySetA = new FuzzyNumber(lab1.initializeSetA(18));
        FuzzyNumber fuzzySetB = new FuzzyNumber(lab1.initializeSetB(18));
        System.out.println("Fuzzy set A " + fuzzySetA);
        System.out.println("Альфа переріз множини А для значення Альфа = 0.4: " + fuzzySetA.getAlphaCut(0.4));
        System.out.println("Fuzzy set B " + fuzzySetB);
        System.out.println("Альфа переріз множини В для значення Альфа = 0.3: " + fuzzySetB.getAlphaCut(0.3));
        System.out.println("******************************************************************************");
        System.out.println("Об’єднання: " + fuzzySetA.getUnionMax(fuzzySetB));
        System.out.println("Перетин: " + fuzzySetA.getIntersectionMax(fuzzySetB));
        System.out.println("Доповнення: " + fuzzySetA.deductMax(fuzzySetB));
        System.out.println("******************************************************************************");
        System.out.println("Додавання: " + fuzzySetA.add(fuzzySetB));
        System.out.println("Віднімання: " + fuzzySetA.subtract(fuzzySetB));
        System.out.println("Множення: " + fuzzySetA.multiply(fuzzySetB));
        System.out.println("Ділення: " + fuzzySetA.divide(fuzzySetB));
    }
}
