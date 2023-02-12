package com.plot3d.plot3d;

public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(double X, double Y, double Z) {
        x = X;
        y = Y;
        z = Z;
    }

    public Vector3D(Point3D first, Point3D sec){
        x = sec.getX() - first.getX();
        y = sec.getY() - first.getY();
        z = sec.getZ() - first.getZ();
    }

    public double ScalarProduct(Vector3D W) {
        return (x * W.getX() + y * W.getY() + z * W.getZ());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector3D VectorProduct(Vector3D W) {
        return new Vector3D(y * W.getZ() - z * W.getY(),
                z * W.getX() - x * W.getZ(),
                x * W.getY() - y * W.getX());
    }

    public Vector3D addVector(Vector3D W) {
        return new Vector3D(x + W.getX(), y + W.getY(), z + W.getZ());
    }

    public Vector3D subtractVector(Vector3D W) {
        return new Vector3D(x - W.getX(), y - W.getY(), z - W.getZ());
    }

    public Vector3D multiplyByNumber(double num) {
        return new Vector3D(x * num, y * num, z * num);
    }
    public void printVector(){
        System.out.println("v = [" + x +", " + y + ", " + z+ "]");
    }
    public double calculateLength(){
        return Math.sqrt(x*x+y*y+z*z);
    }
}
