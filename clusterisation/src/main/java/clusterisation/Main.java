package clusterisation;

import java.applet.Applet;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Main extends Applet implements Runnable {
    private Vector<Cross> CrossList;
    private Vector<Cross> RemovedCrossList;
    private Vector<Quad> Centroids;
    private Choice SubsetChoice;
    private Button RestartButton, ResetButton, RunButton, DrawGButton;
    private Checkbox algorithm;

    private Thread Go;
    private int step;
    private int subset;
    private Random rand;
    private boolean abort;

    private final int k = 10;
    private final int points = 100000;
    private int width = 800;
    private int height = 500;
    private int R = 100;

    public void init() {
        rand = new Random();
        Centroids = new Vector<Quad>();

        RestartButton = new Button("Сброс центров");
        add(RestartButton);
        RestartButton.setEnabled(false);

        ResetButton = new Button("Сброс точек");
        add(ResetButton);
        ResetButton.setEnabled(false);

        RunButton = new Button("Старт");
        add(RunButton);
        RunButton.setEnabled(false);

        DrawGButton = new Button("Разместить точки");
        add(DrawGButton);

        CrossList = new Vector<Cross>();
        RemovedCrossList = new Vector<Cross>();

        SubsetChoice = new Choice();
        for (int i = 2; i <= k; i++) {
            SubsetChoice.addItem(String.valueOf(i));
        }
        add(SubsetChoice);
        SubsetChoice.select(String.valueOf(k));

        algorithm = new Checkbox("Forel");
        add(algorithm);

        subset = 10;
        step = -1;
    }


    public void paint(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawRect(0, 50, width, height);

        Cross s;
        int numShapes;
        numShapes = RemovedCrossList.size();
        for (int i = 0; i < numShapes; i++) {
            s = RemovedCrossList.elementAt(i);
            s.draw(g);
        }
        numShapes = CrossList.size();
        for (int i = 0; i < numShapes; i++) {
            s = CrossList.elementAt(i);
            s.draw(g);
        }
        if (step != -1) {
            Quad t;
            int numCent = Centroids.size();
            for (int i = 0; i < numCent; i++) {
                t = Centroids.elementAt(i);

                t.draw(g);
//                if (algorithm.getState()) {
//                    g.setColor(t.color);
//                    g.drawOval(t.x - R, t.y - R, R * 2, R * 2);
//                }
            }
        }
        g.setColor(Color.black);
    }

    public void run() {
        if (algorithm.getState()) {
            forel();
        } else {
            kmeans();
        }
    }

    private void forel() {
        step = 1;
        R = Integer.valueOf(SubsetChoice.getSelectedItem()) * 10;
        Color color;
        while (!isClusterisationFinished()) {
            color = getRandomColor();
            int numShapes = CrossList.size();
            Cross currentObject = CrossList.get(rand.nextInt(numShapes));
            Quad p = new Quad();
            p.x = currentObject.x;
            p.y = currentObject.y;

            p.color = color;
            Centroids.add(p);
            p = Centroids.lastElement();
            Vector<Cross> neighbourObjects;

            Quad current = new Quad();
            do {
                current.x = p.x;
                current.y = p.y;
                neighbourObjects = getNeighbourObjects(current);
                p.x = getCenterOfObjects(neighbourObjects).x;
                p.y = getCenterOfObjects(neighbourObjects).y;
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            } while (!equals(p, current));

            CrossList.removeAll(neighbourObjects);
            RemovedCrossList.addAll(neighbourObjects);
        }
        RestartButton.setEnabled(true);
        ResetButton.setEnabled(true);
        step = 5;
        abort = true;
        Go.stop();
    }

    private boolean equals(Quad q1, Quad q2) {
        return q1.x == q2.x && q1.y == q2.y;
    }

    private Color getRandomColor() {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    private boolean isClusterisationFinished() {
        return CrossList.size() == 0;
    }

    private Vector<Cross> getNeighbourObjects(Quad centroid) {
        double dist;
        for (Cross cross : CrossList) {
            cross.color = Color.black;
        }
        Vector<Cross> result = new Vector<Cross>();
        for (Cross cross1 : CrossList) {
            dist = Point.distance(cross1.x, cross1.y, centroid.x, centroid.y);
            if (dist <= R) {
                cross1.color = Centroids.lastElement().color;
                result.add(cross1);
            }
        }
        return result;
    }

    private Quad getCenterOfObjects(Vector<Cross> neighbourObjects) {
        Cross s;
        Quad p = new Quad();
        Point sumPoint = new Point();
        sumPoint.x = 0;
        sumPoint.y = 0;
        int Count = 0;
        int numShapes = neighbourObjects.size();
        for (int i = 0; i < numShapes; i++) {
            s = neighbourObjects.elementAt(i);
            sumPoint.x += s.x;
            sumPoint.y += s.y;
            Count++;
        }
        if (Count > 0) {
            p.x = sumPoint.x / Count;
            p.y = sumPoint.y / Count;
        }
        return p;
    }

    private void kmeans() {
        while (true) {
            if (step == -1) this.step1();
            else if (step == 1) this.step2();
            else if (step == 2) this.step3();
            else if (step == 3) step = 4;
            else if ((step == 4) && (abort)) {
                RestartButton.setEnabled(true);
                ResetButton.setEnabled(true);
                step = 5;
                repaint();
                Go.stop();
            } else if ((step == 4) && (!abort)) this.step2();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void step1() {
        abort = false;
        String subsetString = SubsetChoice.getSelectedItem();
        subset = Integer.valueOf(subsetString);
        int numShapes = CrossList.size();
        boolean ch[] = new boolean[numShapes];
        for (int i = 0; i < numShapes; i++) ch[i] = false;
        for (int i = 0; i < subset; ) {
            Cross s;
            Quad p = new Quad();
            int r = Math.abs(rand.nextInt() % numShapes);
            if (!ch[r]) {
                s = CrossList.elementAt(r);
                p.x = s.x;
                p.y = s.y;
                p.color = getRandomColor();

                Centroids.addElement(p);
                ch[r] = true;
                i++;
            }
        }
        step = 1;
    }

    private void step2() {
        Cross s;
        Quad p;
        int numShapes = CrossList.size();
        for (int i = 0; i < numShapes; i++) {
            s = CrossList.elementAt(i);

            int numCent = Centroids.size();
            int min = 0;
            double dist_min = 99999999.9;
            for (int j = 0; j < numCent; j++) {
                p = Centroids.elementAt(j);

                double dist = Point.distance(s.x, s.y, p.x, p.y);
                if (dist < dist_min) {
                    dist_min = dist;
                    min = j;
                }
            }
            p = Centroids.elementAt(min);
            s.color = p.color;
        }
        step = 2;
    }

    private void step3() {
        Quad p;
        Cross s;
        double changes = 0.0;
        Point sumPoint = new Point();
        int numCent = Centroids.size();
        for (int j = 0; j < numCent; j++) {
            p = Centroids.elementAt(j);
            sumPoint.x = 0;
            sumPoint.y = 0;
            int Count = 0;
            int numShapes = CrossList.size();
            for (int i = 0; i < numShapes; i++) {
                s = CrossList.elementAt(i);
                if (s.color == p.color) {
                    sumPoint.x += s.x;
                    sumPoint.y += s.y;
                    Count++;
                }
            }
            if (Count > 0) {
                changes += Point.distance(p.x, p.y, sumPoint.x / Count, sumPoint.y / Count);
                Point pt = new Point();
                pt.x = p.x;
                pt.y = p.y;
                p.x = sumPoint.x / Count;
                p.y = sumPoint.y / Count;
            }
        }
        if (changes < 0.1) abort = true;
        step = 3;
    }


    private void Reset() {
        step = -1;
        abort = false;
        RestartButton.setEnabled(false);
        ResetButton.setEnabled(false);
        RunButton.setEnabled(false);
        Centroids.removeAllElements();
        CrossList.removeAllElements();
        RemovedCrossList.removeAllElements();

        repaint();
    }

    public boolean action(Event event, Object eventobject) {
        if ((event.target == RunButton)) {

            Go = new Thread(this);
            Go.start();
            RestartButton.setEnabled(false);
            ResetButton.setEnabled(false);
            RunButton.setEnabled(false);

            return true;
        }
        if ((event.target == DrawGButton)) {
            if (CrossList.size() > 0 || RemovedCrossList.size() > 0) Reset();

            String subsetString = SubsetChoice.getSelectedItem();
            subset = Integer.valueOf(subsetString);

            Vector<Gaussian> GaussianList;
            GaussianList = new Vector<Gaussian>();
            for (int i = 0; i < subset; i++) {
                Gaussian gaus = new Gaussian();

                gaus.mux = 50 + Math.abs(rand.nextInt() % width);
                gaus.muy = 75 + Math.abs(rand.nextInt() % height);

                gaus.sigma = 10 + Math.abs(30 * rand.nextDouble());

                GaussianList.addElement(gaus);
            }
            ResetButton.setEnabled(true);
            RunButton.setEnabled(true);

            for (int i = 0; i < subset; i++) {

                Gaussian gaus;
                gaus = GaussianList.elementAt(i);

                for (int j = 0; j < points / subset; j++) {

                    double r = 5 * gaus.sigma * Math.pow(rand.nextDouble(), 2);
                    double alpha = 2 * Math.PI * rand.nextDouble();
                    int x = gaus.mux + (int) Math.round(r * Math.cos(alpha));
                    int y = gaus.muy + (int) Math.round(r * Math.sin(alpha));

                    if (allowedMousePosition(x, y)) {

                        Cross s = new Cross();
                        s.color = Color.black;
                        s.x = x;
                        s.y = y;
                        CrossList.addElement(s);
                    }
                }
            }

            repaint();
            return true;
        }

        if ((event.target == RestartButton) && (step != -1)) {
            step = -1;
            abort = false;
            Centroids.removeAllElements();
            CrossList.addAll(RemovedCrossList);
            RemovedCrossList.removeAllElements();
            for (Cross cross : CrossList) {
                cross.color = Color.black;
            }

            ResetButton.setEnabled(true);
            RunButton.setEnabled(true);

            this.repaint();
            return true;
        }
        if ((event.target == ResetButton)) {
            Reset();
            return true;
        }
        return true;
    }

    private boolean allowedMousePosition(int x, int y) {
        return (x >= 5) && (y >= 55) && (x < width) && (y < height + 50);
    }

    public boolean mouseUp(Event e, int x, int y) {

        if ((step == -1) && (allowedMousePosition(x, y))) {
            ResetButton.setEnabled(true);
            RunButton.setEnabled(true);

            Cross s = new Cross();

            s.color = Color.black;
            s.x = x;
            s.y = y;
            CrossList.addElement(s);

            repaint();
        }

        return true;
    }
}
