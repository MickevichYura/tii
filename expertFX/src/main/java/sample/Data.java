package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {
    private int N;
    private int M;
    private List<List<Integer>> matrix;
    private List<String> characteristics;
    private List<String> subjects;

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public Data() {
    }

    public Data(List<List<Integer>> matrix, List<String> characteristics, List<String> subjects) {
        N = characteristics.size();
        M = subjects.size();
        this.matrix = matrix;
        this.characteristics = characteristics;
        this.subjects = subjects;
    }

    private List<Integer> getIndexesWithOne(int minIndex) {
        List<Integer> indexesWithOne = new ArrayList<>();
        for (int j = 0; j < matrix.get(minIndex).size(); j++) {
            if (matrix.get(minIndex).get(j) == 1)
                indexesWithOne.add(j);
        }
        return indexesWithOne;
    }

    private List<Integer> getIndexesWithZero(int minIndex) {
        List<Integer> indexesWithZero = new ArrayList<>();
        for (int j = 0; j < matrix.get(minIndex).size(); j++) {
            if (matrix.get(minIndex).get(j) == 0)
                indexesWithZero.add(j);
        }
        return indexesWithZero;
    }

    int getMinIndex() {
        int minSum = M;
        int minIndex = 0;
        for (int i = 0; i < N; i++) {
            int tempSum = 0;
            for (int j = 0; j < M; j++) {
                tempSum += matrix.get(i).get(j);
            }
            if (tempSum < minSum) {
                minSum = tempSum;
                minIndex = i;
            }
        }
        return minIndex;
    }

    private List<List<Integer>> removeRowsWithAllZero() {
        List<Integer> indexesWithZero = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            int tempSum = 0;
            for (int j = 0; j < matrix.get(0).size(); j++) {
                tempSum += matrix.get(i).get(j);
            }
            if (tempSum == 0) {
                indexesWithZero.add(i);
            }
        }
        Collections.reverse(indexesWithZero);
        for (Integer integer : indexesWithZero) {
            matrix.remove(integer.intValue());
            characteristics.remove(integer.intValue());
        }

        updateSize();
        return matrix;
    }

    private void updateSize() {
        N = matrix.size();
        M = (N == 0) ? 0 : matrix.get(0).size();
    }

    private List<List<Integer>> removeColumn(int index) {
        for (List<Integer> integers : matrix) {
            integers.remove(index);
        }
        subjects.remove(index);
        updateSize();
        return matrix;
    }

    String calculate(String userAnswer) {
        int minIndex = getMinIndex();
        List<Integer> indexesWithZero = getIndexesWithZero(minIndex);
        List<Integer> indexesWithOne = getIndexesWithOne(minIndex);
        if (userAnswer.equals("Yes") || userAnswer.equals("Да")) {
            Collections.reverse(indexesWithZero);
            for (Integer integer : indexesWithZero) {
                matrix = removeColumn(integer);
            }

        } else {
            matrix.remove(minIndex);
            characteristics.remove(minIndex);
            Collections.reverse(indexesWithOne);
            for (Integer integer : indexesWithOne) {
                matrix = removeColumn(integer);
            }
        }
        removeRowsWithAllZero();
        String result;
        if (M == 1) {
            result = "Результат - " + subjects.get(0);
        } else {
            result = "Есть " + characteristics.get(getMinIndex()) + "?";
        }

        return result;
    }

}
