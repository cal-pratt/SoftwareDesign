package eventpkg;

interface IEventListener<EventPublisher extends AEventPublisher<?>> {
    public void actionPerformed(EventPublisher sender, Object e);
}
