import java.util.*;
import java.util.stream.Collectors;

public class FuzzySet {

    protected final Map<Double, Double> set;

    private List<Double> core;
    private Double height;
    private List<Double> mode;
    private List<Double> support;

    public FuzzySet(Map<Double, Double> set) {
        this.set = set;
        calculate();
    }

    public void calculate() {
        core = getCore();
        height = getHeight();
        mode = getMode();
        support = getSupport();
    }

    public List<Double> getCore() {
        core = set.entrySet().stream().filter(entry -> entry.getValue() == 1.0).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return core;
    }

    public double getHeight() {
        height = 0.0;
        for (Double i : set.values()) {
            if (i > height) {
                height = i;
            }
        }
        return height;
    }

    public List<Double> getMode() {
        return set
                .entrySet()
                .stream()
                .filter(entry -> height.equals(entry.getValue()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<Double> getSupport() {
        support = set.entrySet().stream().filter(entry -> entry.getValue() > 0.0).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return support;
    }

    public void normalize() {
        if (getHeight() != 1.0) {
            set.replaceAll((k, v) -> v / height);
            height = 1.0;
        }
        calculate();
    }

    public Map<Double, Double> getUnionMax(FuzzySet anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.set.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.max(entry.getValue(), set.get(entry.getKey())));
            }
        }
        return result;
    }

    public Map<Double, Double> getIntersectionMax(FuzzySet anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.set.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.min(entry.getValue(), set.get(entry.getKey())));
            }
        }
        return result;
    }

    public Map<Double, Double> deductMax(FuzzySet anotherSet) {
        Map<Double, Double> addition = new TreeMap<>();
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            addition.put(entry.getKey(), Double.parseDouble(String.format("%.2f", 1 - entry.getValue())));
        }
        //return getIntersectionMax(new FuzzySet(addition));
        return new FuzzySet(addition).getSet();
    }

    public List<Double> getAlphaCut(double alpha) {
        List<Double> result = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() >= alpha) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public Map<Double, Double> getSet() {
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuzzySet that = (FuzzySet) o;

        return Objects.equals(set, that.set);
    }

    @Override
    public String toString() {
        return "= " + set + ", \n" +
                "ядро = " + core + ", \n" +
                "висота = " + height + ", \n" +
                "носій = " + support + ", \n" +
                "мода = " + mode;
    }
}