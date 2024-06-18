import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
public class Arrow {
  private final Point start = new Point();
  private final Point end = new Point();
  private final Path2D arrowHead = makeArrowHead(new Dimension(8, 8));

  protected Arrow(Point start, Point end) {
    this.start.setLocation(start);
    this.end.setLocation(end);
  }

  protected Path2D makeArrowHead(Dimension size) {
    Path2D path = new Path2D.Double();
    double w = size.width * .5;
    double h = size.height;
    path.moveTo(0d, -w);
    path.lineTo(h, 0d);
    path.lineTo(0d, w);
    path.closePath();
    return path;
  }

  public void draw(Graphics2D g2) {
    g2.drawLine(start.x, start.y, end.x, end.y);
    // arrowHead.transform(AffineTransform.getRotateInstance(end.x - start.x, end.y - start.y));
    // arrowHead.transform(AffineTransform.getTranslateInstance(end.x, end.y));
    AffineTransform at = AffineTransform.getTranslateInstance(end.getX(), end.getY());
    at.rotate(end.getX() - start.getX(), end.getY() - start.getY());
    arrowHead.transform(at);
    g2.fill(arrowHead);
    g2.draw(arrowHead);
  }
}
