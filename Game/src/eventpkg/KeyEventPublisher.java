package eventpkg;

public class KeyEventPublisher extends AEventPublisher<KeyEventPublisher>{
    @Override
    public void publish(Object action) {
        publish(this, action);
    }
}