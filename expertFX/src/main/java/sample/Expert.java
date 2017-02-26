
package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Expert {

    public static Data init() throws FileNotFoundException {

        String basePath = "expertFx/";
        String inputFileName = basePath + "input.txt";
        String subjectsFileName = basePath + "subjects.txt";
        String characteristicsFileName = basePath + "characteristics.txt";
        File inputFile = new File(inputFileName);
        File characteristicsFile = new File(characteristicsFileName);
        File subjectsFile = new File(subjectsFileName);
        Scanner in = new Scanner(inputFile);
        int n = in.nextInt();
        int m = in.nextInt();
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < m; j++) {
                matrix.get(i).add(in.nextInt());
            }
        }

        in = new Scanner(characteristicsFile);
        List<String> characteristics = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            characteristics.add(in.nextLine());
        }

        in = new Scanner(subjectsFile);
        List<String> subjects = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            subjects.add(in.nextLine());
        }

        in.close();

        return new Data(n, m, matrix, characteristics, subjects);
    }

}
