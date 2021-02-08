public class stringexamples {
    public static void main(String[] args) {
        exampleTwo();
        exampleThree();
    }private static void exampleTwo(){
        String cc = "ICS3UAP";
        int x = cc.length();
        String sub = cc.substring(2, 5); //"S3U" starts at index 2 and ends at index 5
        String sub2 = cc.substring(2);  //"SCU AP" starts at index 2 then goes to the end
        System.out.println("The length of " + cc + " is " + x);
        System.out.println(sub + "\n" + sub2);
    }private static void exampleThree(){
        String s1 = new String("Steve"); //going to build a new string every time
        String s2 = new String("Steve");
        String s3 = "Steve"; //this is not a new string that is being built, it will reuse an existing "Steve" string.
        String s4 = "Steve"; //string literal, java stores all the string literals in a table and reuses them
        System.out.println(s1 == s2); //equality operator
        System.out.println(s3 == s4);
        System.out.println(s1.equals(s2));
        /**
         * never use == to compare strings, always use .equals
         */
    }
}
