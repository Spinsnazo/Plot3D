package com.plot3d.plot3d;

public class Point3D extends Point2D{
    private double z;

    public Point3D(){
        super();
        z = 0;
    }

    public Point3D(double X, double Y, double Z) {
        super(X, Y);
        z = Z;
    }
    public Point3D(Point3D sec) {
        x = sec.x;
        y = sec.y;
        z = sec.z;
    }

    public void TranslateByVector(Vector3D V) {
        x += V.getX();
        y += V.getY();
        z += V.getZ();
    }

    public Point2D CastTo2D(double z0, double d) throws DivisionByZeroException {
        if (z0 - z == 0) {
            throw new DivisionByZeroException();
        }
        return new Point2D((z0 * x - z * d) / (z0 - z), (z0 * y) / (z0 - z));
    }
    
    public double getZ() {
        return z;
    }

    public void PrintPoint() {
        System.out.println("P = (" + x + ", " + y + ", " + z + ")");
    }

    public void PrintCoordinates()
    {
        System.out.println("x = " + x + ", y = " + y + ", z = " + z);
    }

    public void rotateOX(double angle){
        final double rad = Math.toRadians(angle);
        final double sin = Math.sin(rad);
        final double cos = Math.cos(rad);
        y = cos*y - sin*z;
        z = sin*y + cos*z;
    }

    public void rotateOY(double angle){
        final double rad = Math.toRadians(angle);
        final double sin = Math.sin(rad);
        final double cos = Math.cos(rad);
        x = cos*x + sin*z;
        z = -sin*x + cos*z;
    }

    public void rotateOZ(double angle){
        final double rad = Math.toRadians(angle);
        final double sin = Math.sin(rad);
        final double cos = Math.cos(rad);
        x = cos*x - sin*y;
        y = sin*x + cos*y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point3D sec = (Point3D) o;
        return (x == sec.x && y == sec.y && z == sec.z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
}
