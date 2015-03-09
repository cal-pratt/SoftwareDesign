package eventpkg;

public class JoystickEventPublisher extends AEventPublisher<JoystickEventPublisher> {
    @Override
    public void publish(Object action) {
        publish(this, action);
    }
}
