package nioserver.acceptors;

public interface Acceptor<T> {
    T accept();
}
