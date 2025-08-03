/***
Coder: Roslan S, UiTM Pahang, roslancs@uitm.edu.my
Year: 2012
***/

public class Queue <E> 
{
    private LinkedList<E> linkedList;
    
    public Queue() {
        linkedList = new LinkedList<>();
    }
    
    public void enqueue(E element) {
        linkedList.addLast(element);
    }
    
    public E dequeue() {
        return linkedList.removeFirst();
    }
    
    public E getFront() {
        return linkedList.getFirst();
    }
    
    public E getEnd() {
        return linkedList.getLast();
    }
    
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }
    
    public String toString() {
        return linkedList.toString();
    }
}
