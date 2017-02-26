
package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Expert {
    private static String basePath = "expertFX/src/main/resources/sample/";

    public static Data init() throws FileNotFoundException {

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

        return new Data(matrix, characteristics, subjects);
    }

    public static void write(Data data) throws IOException {

        String inputFileName = basePath + "input.txt";
        String subjectsFileName = basePath + "subjects.txt";
        String characteristicsFileName = basePath + "characteristics.txt";
        File inputFile = new File(inputFileName);
        File characteristicsFile = new File(characteristicsFileName);
        File subjectsFile = new File(subjectsFileName);

        int n = data.getN();
        int m = data.getM();
        FileWriter fileWriter = new FileWriter(inputFile);
        fileWriter.write(String.valueOf(n));
        fileWriter.write(" ");
        fileWriter.write(String.valueOf(m));
        System.out.println(String.valueOf(n));
        System.out.println(String.valueOf(m));

        List<List<Integer>> matrix = data.getMatrix();
        for (int i = 0; i < n; i++) {
            fileWriter.write("\n");
            for (int j = 0; j < m; j++) {
                fileWriter.write(String.valueOf(matrix.get(i).get(j)));
                fileWriter.write(" ");
            }
        }

        fileWriter.close();
        fileWriter = new FileWriter(characteristicsFile);
        List<String> characteristics = data.getCharacteristics();
        for (int i = 0; i < n; i++) {
            fileWriter.write(characteristics.get(i) + "\n");
        }

        fileWriter.close();
        fileWriter = new FileWriter(subjectsFile);
        List<String> subjects = data.getSubjects();
        for (int i = 0; i < m; i++) {
            fileWriter.write(subjects.get(i) + "\n");
        }

        fileWriter.close();
    }

}
