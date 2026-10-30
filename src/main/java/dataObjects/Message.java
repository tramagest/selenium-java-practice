package dataObjects;

public enum Message {
    // Home / Register
    WELCOME_USER("Welcome %s"),
    HOME_PAGE_TITLE("Welcome to Safe Railway"),
    HOME_CREATE_ACCOUNT_LINK("create an account"),
    REGISTER_PAGE_TITLE("Create account"),
    REGISTER_SUCCESS_TITLE("Thank you for registering your account"),
    REGISTER_CONFIRMED("Registration Confirmed! You can now log in to the site"),
    REGISTER_EMAIL_IN_USE("This email address is already in use."),
    REGISTER_FORM_ERROR("There're errors in the form. Please correct the errors and try again."),
    INVALID_PASSWORD_LENGTH("Invalid password length."),
    INVALID_PID_LENGTH("Invalid ID length."),

    // Login
    LOGIN_BLANK_USERNAME_ERROR("There was a problem with your login and/or errors exist in your form. "), // TC02: có dấu cách cuối
    LOGIN_FORM_ERROR("There was a problem with your login and/or errors exist in your form."),             // TC03
    LOGIN_WRONG_PASSWORD_ERROR("Invalid username or password. Please try again"),                         // TC04: không có dấu chấm
    LOGIN_NOT_ACTIVATED_ERROR("Invalid username or password. Please try again."),                         // TC05: có dấu chấm
    LOGIN_ATTEMPTS_WARNING("You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes."),

    // Reset password
    RESET_FORM_TITLE("Password Change Form"),
    RESET_SAME_PASSWORD("The new password cannot be the same with the current password"),
    RESET_FAILED("Could not reset password. Please correct the errors and try again."),
    RESET_CONFIRM_MISMATCH("The password confirmation did not match the new password."),

    // Ticket
    BOOK_SUCCESS("Ticket booked successfully!"),
    TICKET_PRICE_PAGE_TITLE("Ticket Price"),
    TICKET_PRICE_TABLE_TITLE("Ticket price from %s to %s"),

    // Email subject
    EMAIL_CONFIRM_SUBJECT("Please confirm your account"),
    EMAIL_RESET_SUBJECT("Please reset your password");

    private final String text;

    Message(String text) { this.text = text; }

    public String getText() { return text; }

    public String format(Object... args) { return String.format(text, args); }
}