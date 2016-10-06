import javax.swing.*;
import java.awt.*;

/**
 * Created by hiro on 16-10-6.
 */
public class GuoQi extends JFrame{

    private final int width = 800;
    private final int height = 800;
    private double maxR = 0.15, minR = 0.05;
    private double maxX = 0.50, maxY = 0.50;
    private double[] minX = {0.75, 0.85, 0.85, 0.75};
    private double[] minY = {0.35, 0.45, 0.60, 0.70};

    public GuoQi() {
        setTitle("National Flag");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, width, height);
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        graphics.setColor(Color.RED);
        graphics.fillRect(100, 100, 450, 300);

        graphics.setColor(Color.yellow);
        paintP(graphics, 100+70, 100+80, 45, 0);
        paintP(graphics, 100+135, 100+30, 15, 36);
        paintP(graphics, 100+165, 100+60, 15, 54);
        paintP(graphics, 100+165, 100+105, 15, 0);
        paintP(graphics, 100+135, 100+150, 15, 18);
    }

    public void paintP(Graphics g,int x0,int y0,int r, int degree)
    {
        double ch=72*Math.PI/180;//圆心角的弧度数
        double de = Math.abs(degree%72)*Math.PI/180;
        int x1 = (int)(x0 + r*Math.sin(de)),
            x2=(int)(x0-Math.sin(ch-de)*r),
            x3=(int)(x0+Math.cos(de-ch/4)*r),
            x4=(int)(x0-Math.sin(ch/2+de)*r),
            x5=(int)(x0+Math.sin(ch/2-de)*r);
        int y1=(int)(y0-r*Math.cos(de)),
            y2=(int)(y0-Math.cos(ch-de)*r),
            y3=(int)(y0+r*Math.sin(de-ch/4)),
            y4=(int)(y0+Math.cos(ch/2+de)*r),
            y5=(int)(y0+Math.cos(ch/2-de)*r);

        int bx=(int)(x0+r*Math.sin(ch/2+de)*Math.cos(ch)/Math.cos(ch/2));
        int by=(int)(y0-r*Math.cos(ch/2+de)*Math.cos(ch)/Math.cos(ch/2));

        Polygon a=new Polygon();
        Polygon b=new Polygon();
        a.addPoint(x2,y2);
        a.addPoint(x5,y5);
        a.addPoint(bx,by);
        b.addPoint(x1,y1);
        b.addPoint(bx,by);
        b.addPoint(x3,y3);
        b.addPoint(x4,y4);

        g.fillPolygon(a);
        g.fillPolygon(b);

    }

    public static void main(String[] args) {
        GuoQi flag = new GuoQi();
    }

}
