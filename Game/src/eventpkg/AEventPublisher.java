package eventpkg;

import java.util.ArrayList;
import java.util.List;

abstract class AEventPublisher<T extends AEventPublisher<?>> {
    private List<IEventListener<T>> listeners;
    
    public AEventPublisher(){
        listeners = new ArrayList<IEventListener<T>>();
    }

    public void subscribe(IEventListener<T> listener) {
        listeners.add(listener);
    }
    
    public void unsubscribe(IEventListener<T> listener) {
        listeners.remove(listener);
    }

    protected void publish(T sender, Object e) {
        for(IEventListener<T> listener : listeners){
            listener.actionPerformed(sender, e);
        }
    }
    public abstract void publish(Object e);
}
