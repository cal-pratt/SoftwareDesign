package eventpkg;

public class PlayerEventPublisher extends AEventPublisher<PlayerEventPublisher> {
    @Override
    public void publish(Object action) {
        publish(this, action);
    }
}
