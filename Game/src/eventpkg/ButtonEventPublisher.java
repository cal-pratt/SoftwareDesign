package eventpkg;

public class ButtonEventPublisher extends AEventPublisher<ButtonEventPublisher> {
    @Override
    public void publish(Object action) {
        publish(this, action);
    }
}
