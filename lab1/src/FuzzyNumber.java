import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

public class FuzzyNumber extends FuzzySet {

    public FuzzyNumber(Map<Double, Double> set) {
        super(set);
    }

    public Map<Double, Double> add(FuzzyNumber anotherNumber) {
        return action(anotherNumber, Double::sum);
    }

    public Map<Double, Double> subtract(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first - second);
    }

    public Map<Double, Double> multiply(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first * second);
    }

    public Map<Double, Double> divide(FuzzyNumber anotherNumber) {
        return action(anotherNumber, (first, second) -> first / second);
    }

    private Map<Double, Double> action(FuzzyNumber anotherSet, BiFunction<Double, Double, Double> function) {
        Map<Double, Double> result = new TreeMap<>();
        for (Map.Entry<Double, Double> entrySet : set.entrySet()) {
            for (Map.Entry<Double, Double> anotherEntrySet : anotherSet.set.entrySet()) {
                double key = function.apply(entrySet.getKey(), anotherEntrySet.getKey());
                double value = Math.min(entrySet.getValue(), anotherEntrySet.getValue());
                if (result.containsKey(key)) {
                    double oldValue = result.get(key);
                    value = Math.max(value, oldValue);
                }
                if (!Double.isNaN(key) && !Double.isInfinite(key)) {
                    result.put(Double.parseDouble(String.format("%.2f", key)), value);
                }
            }
        }
        return result;
    }
}