package clusterisation;

import java.awt.*;

class Cross {
    private static final int shapeRadius = 2;
    Color color;
    int x;
    int y;

    void draw(Graphics g) {
        g.setColor(this.color);
        g.drawLine(this.x - shapeRadius, this.y, this.x + shapeRadius, this.y);
        g.drawLine(this.x, this.y - shapeRadius, this.x, this.y + shapeRadius);
    }
}
