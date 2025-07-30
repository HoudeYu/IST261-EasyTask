package easytask;

/**
 * Displays a motivational quote as a notification.
 * Unrelated to tasks; purely for mood boosting.
 * Implements Notifiable interface.
 * Author: Houde Yu
 */
public class MotivationalQuote implements Notifiable {
    private String quote;

    public MotivationalQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String sendNotification() {
        return "💬 Motivation: \"" + quote + "\"";
    }
}
