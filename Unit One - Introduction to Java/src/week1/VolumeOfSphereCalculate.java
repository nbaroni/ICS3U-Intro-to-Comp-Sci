public class VolumeOfSphereCalculate {
    public static void main(String[] args) {
        double r = 3; //this is the radius of the sphere
        r = r * r * r;
        double a = Math.PI * 4.0/3.0 * r;
        System.out.println("The volume is " + a); //output statement
    }
}
