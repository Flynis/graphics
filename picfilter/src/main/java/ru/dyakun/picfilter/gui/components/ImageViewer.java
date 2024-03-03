package ru.dyakun.picfilter.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private Dimension size;
    private final JScrollPane scrollPane;
    private BufferedImage image = null;
    private Dimension imageSize = null;
    private int mouseX = 0;
    private int mouseY = 0;
    private final double zoom = 0.05;

    public ImageViewer(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        this.scrollPane.setWheelScrollingEnabled(false);
        this.scrollPane.setDoubleBuffered(true);
        this.scrollPane.setViewportView(this);
        this.scrollPane.setBackground(Colors.DARK_BACK_COLOR);
        this.scrollPane.validate();

        size = getVisibleRectSize();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        if (image == null) {
            g.setColor(Colors.DARK_BACK_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        else {
            g.drawImage(image, 0, 0, size.width, size.height, null);
        }
    }

    public void setImage(BufferedImage im, boolean fitScreen) {
        image = im;
        if (image == null) {
            setMaxVisibleRectSize();
            repaint();
            revalidate();
            return;
        }

        Dimension newSize = new Dimension(image.getWidth(), image.getHeight());
        if (imageSize == null) {
            fitScreen = true;
        } else if ((newSize.height != imageSize.height) || (newSize.width != imageSize.width)) {
            fitScreen = true;
        }
        imageSize = newSize;

        if (fitScreen) {
            setMaxVisibleRectSize();
            double kh = (double) imageSize.height / size.height;
            double kw = (double) imageSize.width / size.width;
            double k = Math.max(kh, kw);

            size.width = (int)(imageSize.width / k);
            size.height = (int)(imageSize.height / k);

            scrollPane.getViewport().setViewPosition(new Point(0,0));
            revalidate();
            scrollPane.paintAll(scrollPane.getGraphics());
        } else {
            scrollPane.paintAll(scrollPane.getGraphics());
        }
    }

    public void fitScreen()	{ setImage(image, true); }

    public void realSize() {
        if (image == null) {
            return;
        }
        int k = (int) (imageSize.width / (double) size.width);
        Point scroll = scrollPane.getViewport().getViewPosition();
        scroll.x *= k;
        scroll.y *= k;
        size.setSize(imageSize);

        revalidate();
        scrollPane.getHorizontalScrollBar().setValue(scroll.x);
        scrollPane.getVerticalScrollBar().setValue(scroll.y);
        scrollPane.paintAll(scrollPane.getGraphics());
    }

    private Dimension getVisibleRectSize() {
        Dimension viewportSize = scrollPane.getViewport().getSize();
        if (viewportSize.height == 0) {
            return new Dimension( scrollPane.getWidth()-3, scrollPane.getHeight()-3 );
        } else {
            return viewportSize;
        }
    }

    private void setMaxVisibleRectSize() {
        size = getVisibleRectSize();
        revalidate();
        scrollPane.validate();
        size = getVisibleRectSize();
        revalidate();
    }

    private void setView(Rectangle rect, int minSize) {
        if (image == null) {
            return;
        }
        if (imageSize.width < minSize || imageSize.height < minSize) {
            return;
        }

        if (rect.width < minSize) {
            rect.width = minSize;
        }
        if (rect.height < minSize) {
            rect.height = minSize;
        }
        if (rect.x < 0) {
            rect.x = 0;
        }
        if (rect.y < 0) {
            rect.y = 0;
        }
        if (rect.x > imageSize.width - minSize) {
            rect.x = imageSize.width - minSize;
        }
        if (rect.y > imageSize.height - minSize) {
            rect.y = imageSize.height - minSize;
        }
        if ((rect.x + rect.width) > imageSize.width) {
            rect.width = imageSize.width - rect.x;
        }
        if ((rect.y + rect.height) > imageSize.height) {
            rect.height = imageSize.height - rect.y;
        }

        Dimension viewSize = getVisibleRectSize();
        double kw = (double)rect.width / viewSize.width;
        double kh = (double)rect.height / viewSize.height;
        double k = Math.max(kh, kw);

        int newPW = (int)(imageSize.width / k);
        int newPH = (int)(imageSize.height / k);
        if (newPW == (int)(newPW * (1 - 2 * zoom))) {
            setView(rect, minSize * 2);
            return;
        }
        size.width = newPW;
        size.height = newPH;

        revalidate();
        scrollPane.validate();

        int xc = rect.x + rect.width / 2;
        int yc = rect.y + rect.height / 2;
        xc = (int)(xc / k);
        yc = (int)(yc / k);
        scrollPane.getViewport().setViewPosition(new Point(xc - viewSize.width / 2, yc - viewSize.height / 2));
        revalidate();
        scrollPane.paintAll(scrollPane.getGraphics());
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (image == null) {
            return;
        }

        double k = 1 - e.getWheelRotation() * zoom;
        int newPW = (int)(size.width * k);
        if (newPW == (int)(newPW * (1 + zoom))) {
            return;
        }
        if (k > 1) {
            int newPH = (int)(size.height * k);
            Dimension viewSize = getVisibleRectSize();
            int pixSizeX = newPW / imageSize.width;
            int pixSizeY = newPH / imageSize.height;
            if (pixSizeX > 0 && pixSizeY > 0) {
                int pixNumX = viewSize.width / pixSizeX;
                int pixNumY = viewSize.height / pixSizeY;
                if (pixNumX < 2 || pixNumY < 2)
                    return;
            }
        }

        size.width = newPW;
        size.height = (int) ((long) size.width * imageSize.height / imageSize.width);

        int x = (int) (e.getX() * k);
        int y = (int) (e.getY() * k);
        Point scroll = scrollPane.getViewport().getViewPosition();
        scroll.x -= e.getX();
        scroll.y -= e.getY();
        scroll.x += x;
        scroll.y += y;

        repaint();
        revalidate();
        scrollPane.validate();

        scrollPane.getHorizontalScrollBar().setValue(scroll.x);
        scrollPane.getVerticalScrollBar().setValue(scroll.y);
        scrollPane.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point scroll = scrollPane.getViewport().getViewPosition();
        scroll.x += ( mouseX - e.getX() );
        scroll.y += ( mouseY - e.getY() );

        scrollPane.getHorizontalScrollBar().setValue(scroll.x);
        scrollPane.getVerticalScrollBar().setValue(scroll.y);
        scrollPane.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();
        if (Math.abs(x1 - mouseX) < 5 && Math.abs(y1 - mouseY) < 5) {
            return;
        }
        double k = (double) imageSize.width / size.width;
        int x0 = (int)(k * mouseX);
        int y0 = (int)(k * mouseY);
        x1 = (int)(k * x1);
        y1 = (int)(k * y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        if (x1 < x0) {
            x0 = x1;
        }
        if (y1 < y0) {
            y0 = y1;
        }
        Rectangle rect = new Rectangle(x0, y0, w, h);
        setView(rect, 10);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){}

}
