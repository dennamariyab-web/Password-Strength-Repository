
import java.util.*;

public class password {

    // Common passwords / dictionary words
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(
        Arrays.asList(
            "password",
            "password123",
            "123456",
            "123456789",
            "qwerty",
            "admin",
            "admin123",
            "welcome",
            "iloveyou",
            "abc123",
            "letmein",
            "football",
            "monkey",
            "dragon"
        )
    );

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       PASSWORD STRENGTH CHECKER");
        System.out.println("======================================");

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
            sc.close();
            return;
        }

        // Check password policy
        boolean length = password.length() >= 8;
        boolean uppercase = password.matches(".*[A-Z].*");
        boolean lowercase = password.matches(".*[a-z].*");
        boolean digit = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[^a-zA-Z0-9].*");

        // Display policy results
        System.out.println("\n------ PASSWORD POLICY ------");

        System.out.println("Minimum 8 characters: " + check(length));
        System.out.println("Uppercase letter:     " + check(uppercase));
        System.out.println("Lowercase letter:     " + check(lowercase));
        System.out.println("Digit:                " + check(digit));
        System.out.println("Special character:    " + check(special));

        // Dictionary check
        boolean dictionaryLeak = COMMON_PASSWORDS.contains(
            password.toLowerCase()
        );

        System.out.println("\n------ DICTIONARY CHECK ------");

        if (dictionaryLeak) {
            System.out.println("WARNING: Password is commonly used!");
        } else {
            System.out.println("Password not found in common password list.");
        }

        // Calculate character pool size
        int pool = 0;

        if (lowercase) pool += 26;
        if (uppercase) pool += 26;
        if (digit) pool += 10;
        if (special) pool += 33;

        // Calculate entropy
        double entropy = 0;

        if (pool > 0) {
            entropy = password.length() *
                      (Math.log(pool) / Math.log(2));
        }

        System.out.println("\n------ ENTROPY CALCULATION ------");

        System.out.println("Password Length: " + password.length());
        System.out.println("Character Pool: " + pool);
        System.out.printf("Password Entropy: %.2f bits%n", entropy);

        // Calculate policy compliance
        int score = 0;

        if (length) score++;
        if (uppercase) score++;
        if (lowercase) score++;
        if (digit) score++;
        if (special) score++;

        // Classify password strength
        String strength;

        if (dictionaryLeak || score <= 2 || entropy < 28) {
            strength = "WEAK";
        } else if (score == 3 || entropy < 40) {
            strength = "MODERATE";
        } else if (score == 4 || entropy < 60) {
            strength = "STRONG";
        } else {
            strength = "EXCEPTIONAL";
        }

        if (dictionaryLeak) {
            strength = "WEAK";
        }

        System.out.println("\n------ PASSWORD STRENGTH ------");
        System.out.println("Strength: " + strength);

        // Give suggestions
        System.out.println("\n------ SUGGESTIONS ------");

        if (!length)
            System.out.println("- Use at least 8 characters.");

        if (!uppercase)
            System.out.println("- Add an uppercase letter.");

        if (!lowercase)
            System.out.println("- Add a lowercase letter.");

        if (!digit)
            System.out.println("- Include at least one digit.");

        if (!special)
            System.out.println("- Include a special character.");

        if (dictionaryLeak)
            System.out.println("- Avoid common or leaked passwords.");

        if (entropy < 40)
            System.out.println("- Use a longer and less predictable password.");

        if (score == 5 && !dictionaryLeak && entropy >= 40) {
            System.out.println("Great! Your password meets all policy criteria.");
        }

        System.out.println("\n======================================");
        System.out.println("       CHECK COMPLETED");
        System.out.println("======================================");

        sc.close();
    }

    // Method to display pass/fail
    public static String check(boolean condition) {
        return condition ? "PASS" : "FAIL";
    }
}
