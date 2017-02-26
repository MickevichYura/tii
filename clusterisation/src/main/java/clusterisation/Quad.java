package clusterisation;

import java.awt.*;

class Quad {
    private static final int shapeRadius = 12;
    Color color;
    int x;
    int y;

    void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.x, this.y, shapeRadius, shapeRadius);
        g.setColor(Color.black);
        g.drawOval(this.x, this.y, shapeRadius, shapeRadius);

    }
}
