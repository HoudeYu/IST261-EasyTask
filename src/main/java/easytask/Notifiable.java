package easytask;

/**
 * Represents a behavior that allows sending a notification.
 * Implemented by different classes in their own way.
 * @author Houde Yu
 */
public interface Notifiable {
    /**
     * Send a custom notification message.
     * @return message string
     */
    String sendNotification();
}
