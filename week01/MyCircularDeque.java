/**
 * 641. 设计循环双端队列
 */
class MyCircularDeque {
    int[] elements;
    int front;
    int rear;
    int size;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        this.elements = new int[k];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (this.isFull()) {
            return false;
        }
        // front = 0 时，将头移到数组的末尾
        int f = this.front - 1 >= 0 ? this.front - 1 : this.elements.length - 1;
        this.elements[this.front = f] = value;
        this.size++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (this.isFull()) {
            return false;
        }
        this.elements[this.rear++] = value;
        if (this.rear == this.elements.length) {
            this.rear = 0;
        }
        this.size++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (this.isEmpty()) {
            return false;
        }
        this.front++;
        if (this.front == this.elements.length) {
            this.front = 0;
        }
        this.size--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (this.isEmpty()) {
            return false;
        }
        int r = this.rear - 1 >= 0 ? this.rear - 1 : this.elements.length - 1;
        this.rear = r;
        this.size--;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        return this.isEmpty() ? -1 : this.elements[this.front];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        int r = this.rear - 1 >= 0 ? this.rear - 1 : this.elements.length - 1;
        return this.isEmpty() ? -1 : this.elements[r];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return this.size == this.elements.length;
    }
}
