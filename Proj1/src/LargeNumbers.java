/**
 * Created by Alex Mancheno on 3/20/17.
 */

 /* The way a LargeNumber is represnted is the following way:
        input: 12,233,444,000
        LargeNumber: (000) -> (444) -> (233) -> (12)
                     (head)                     (tail)
	This felt like the most intuitive way to represent LargeNumbers since our algorithms in
	adding/multiplying by hand usually iterate through the numbers in this way, which seems like
	the good way to do it. Thus, the ‘add’ and ‘multiply’ function follow closely those same algorithms.
  */
public class LargeNumbers {

    private Node head;
    private Node tail;

    public LargeNumbers() {
        this(0);
    }

    //constructor for when parameter is a string. however, this constructor only
    //accepts numbers in the "x,xxx,xxx" format.
    public LargeNumbers(String number) {
        String[] numArray = number.split(",");
        boolean firstNode = true;
        for (int i = numArray.length - 1; i >= 0; i--) {
            Node n = new Node(Integer.parseInt(numArray[i]));
            if (firstNode) {
                head = n;
                tail = n;
                firstNode = false;
            } else {
                tail.setNext(n);
                tail = n;
            }
        }

    }

    //constructor for when parameter is an integer
    public LargeNumbers(int number) {
        head = new Node(number % 1000);
        tail = head;
        number /= 1000;
        while (number != 0) {
            Node n = new Node(number % 1000);
            tail.setNext(n);
            tail = n;
            number /= 1000;
        }
    }

    public static LargeNumbers add(LargeNumbers n1, LargeNumbers n2) {
        /* I add both LargeNumbers by iterating through both of them simultaneously, and adding each of their
         * corresponding nodes (in the ith position), and stop when both numbers have been fully iterated. If
	 * there is a carry, create one final node and add that to the end of the answer.*/
        LargeNumbers answer = new LargeNumbers();
        boolean isFirstNode = true;
        int sum = 0, carry = 0;

        /**/
        Node i = n1.head;
        Node j = n2.head;

        while (i != null || j != null) {
            if (i != null) sum += i.getData();
            if (j != null) sum += j.getData(); // Only add to sum when they are not null.

            sum += carry;
            carry = sum / 1000;
            Node n = new Node(sum % 1000);

            if (isFirstNode) { // Change first node of the answer to result of first sum if the answer
                answer.head = n; // has not been initalized yet.
                answer.tail = n;
                isFirstNode = false;
            } else {
                answer.tail.setNext(n);
                answer.tail = n;
            }

            sum = 0; // Reset sum after it has been added

            // Only go to the next node on each LargeNumber if the iterator on that number is not already null.
            if (i != null) i = i.getNext();
            if (j != null) j = j.getNext();

        }

        // This here is to handle the carry after the while loop above has finished. If there is a carry, it is added
        // to the end of the list.
        if (carry > 0) {
            Node n = new Node(carry);
            answer.tail.setNext(n);
            answer.tail = n;
        }

        return answer;
    } //close method

    public static LargeNumbers multiply(LargeNumbers n1, LargeNumbers n2) {
        /* The procedure here is similar to how we multiply by hand, except that
        *  each partial product is stored directly to the answer instead of keeping each
	*  partial product and waiting to add all of them at the end of the multiplication.
        */
        LargeNumbers answer = new LargeNumbers();
        Node top, bot;

        if (n1.size() > n2.size()) { // To place the larger of the two numbers
            top = n1.head;          // in the inner for-loop below
            bot = n2.head;
        } else {
            top = n2.head;
            bot = n1.head;
        }

        if (bot.getData() == 0 && bot.getNext() == null) {
            return new LargeNumbers("0"); // Return 0 if one of the numbers is 0
        }

        boolean firstNode = true;
        int shifts = 0;
        for (; bot != null; bot = bot.getNext()) {
            LargeNumbers partialProduct = new LargeNumbers();

            Node n;
            int carry = 0;
            for (Node i = top; i != null; i = i.getNext()) {
                int p = bot.getData() * i.getData() + carry;
                carry = p / 1000;
                n = new Node(p % 1000);

                if (firstNode) {
                    partialProduct.head = n;
                    partialProduct.tail = n;
                    firstNode = false;
                } else {
                    partialProduct.tail.setNext(n);
                    partialProduct.tail = n;
                }

            } //close inner loop

            if (carry > 0) {
                n = new Node(carry);
                partialProduct.tail.setNext(n);
                partialProduct.tail = n;
            }

            for (int s = shifts; s > 0; s--) {
                Node shiftNode = new Node(0);
                shiftNode.setNext(partialProduct.head);
                partialProduct.head = shiftNode;

            }

            shifts++;

            firstNode = true;
            answer = add(answer, partialProduct);

        } //close outer loop
        return answer;
    } //close method

    // Helper method so that the larger number goes in the inner for-loop inside
    // the 'multiply' function
    private int size() {
        int answer = 0;
        for (Node i = this.head; i != null; i = i.getNext())
            answer++;
        return answer;
    }

    // Representation of a LargeNumber follows the ’12,402,000’ format
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        Node i = head;
        for (; i.getNext() != null; i = i.getNext()) {
            String num = ",";
            if (i.getData() < 10) num += "00";
            else if (i.getData() < 100) num += "0";
            num += i.toString();
            sb.insert(0, num);
        }

        if (head != null)
            sb.insert(0, i.toString());

        String answer = sb.toString();

        return answer;
    }
}
