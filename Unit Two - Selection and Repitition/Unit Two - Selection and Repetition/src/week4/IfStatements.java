package week4;
public class IfStatements {
    public static void main(String[] args) {
        exampleOne();
    }

    private static void exampleOne() {
        int mark = 87;
        String letterGrade = "F";

        if(mark >= 90){
            letterGrade = "A+";
        } else if (mark >= 80){
            letterGrade = "A";
        }else if(mark >= 70){
            letterGrade = "B";
        }else if(mark >= 60){
            letterGrade = "C";
        }else if(mark >= 50){
            letterGrade = "D";
        }
        System.out.println("You got an " + letterGrade);
    }
}
