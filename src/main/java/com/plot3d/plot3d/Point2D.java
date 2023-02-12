package com.plot3d.plot3d;

public class Point2D{

    protected double x;
    protected double y;

    public Point2D(){
        x = 0;
        y = 0;
    }

    public Point2D(double X, double Y) {
        x = X;
        y = Y;
    }

    public Point2D(Point2D sec) {
        x = sec.x;
        y = sec.y;
    }

    public void printCoordinates() {
        System.out.println("x = " + x + " y = " + y);
    }

    public void printPoint() {
        System.out.println("P = (" + x + ", " + y + ")");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point2D sec = (Point2D) o;
        return (x == sec.x && y == sec.y);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
}
